package my.interceptor;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import my.constant.CacheConstant;
import my.constant.TokenConstant;
import my.domain.LoginEncryptBO;
import my.domain.MyUser;
import my.redis.MyRedisUtil;
import my.util.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import result.RespBean;
import result.RespBeanEnum;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Sun
 * @date 2023/8/18 16:31
 */
@Component
public class MyInterceptor implements HandlerInterceptor {

    static String CONTENT_TYPE = "text/json;charset=utf-8";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1、先从Header读取token
        String token = ServletUtil.getHeader(request, TokenConstant.TOKEN_NAME, CharsetUtil.CHARSET_UTF_8);
        // 2、从cookie获取token
        if (StrUtil.isBlank(token)) {
            Cookie cookie = ServletUtil.getCookie(request, TokenConstant.TOKEN_NAME);
            token = cookie.getValue();
        }
        if (StrUtil.isBlank(token)) {
            ServletUtil.write(response, JSONUtil.toJsonPrettyStr(RespBean.error(RespBeanEnum.NOT_LOGIN)), CONTENT_TYPE);
            return false;
        }
        // 3、拼接Redis中的key
        String redisKey = TokenConstant.TOKEN_PREFIX + token;

        // 4、解析、走Redis判断
        LoginEncryptBO decodeToken = TokenUtil.decode(redisKey);
        String redisCacheUser = decodeToken.getToken();
        if (StrUtil.isBlank(redisCacheUser)) {
            ServletUtil.write(response, JSONUtil.toJsonPrettyStr(RespBean.error(RespBeanEnum.NOT_LOGIN)), CONTENT_TYPE);
            return false;
        }
        // *5、如果要续期，就加个刷新的expire
        if (MyRedisUtil.getExpire(redisKey) < TokenConstant.REFRESH_TIME) {
            MyRedisUtil.expire(redisKey, CacheConstant.EXPIRE_TIME);
        }
        // 6、添加到当前线程，以便获取
        MyUser myUser = JSONUtil.toBean(redisCacheUser, MyUser.class);
        LoginUserUtils.addCurrentUser(myUser);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginUserUtils.remove();
    }
}
