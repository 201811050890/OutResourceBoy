package my;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import my.constant.CacheConstant;
import my.constant.TokenConstant;
import my.domain.LoginEncryptBO;
import my.domain.MyUser;
import my.redis.MyRedisUtil;
import my.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import result.RespBean;
import result.RespBeanEnum;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 这一块主要是自定义的登录、校验
 * 主要依赖：hu-tool，我jwt也用的hu-tool里的
 * @author OutResource Boy
 * @date 2023/8/18 14:51
 */
@Controller
@RequestMapping("/my-sso")
public class SsoController {

    /**
     * 单点登陆
     * 情况1：sso服务端，将请求发到子系统的后端。校验登录后，由后端重定向到子系统前端
     *
     * @param response 响应
     * @param ticket   票据(一般来说都是给个ticket，最多就改个名字【比如：code】。当然可能会传其他的参数)
     */
    @RequestMapping("/login")
    public void mySsoLoginBackendRedirect(HttpServletResponse response,
                                          @RequestParam(value = "ticket") String ticket){
        if (StrUtil.isBlank(ticket)){
            // 抛个异常，记个日志。。。
            throw new RuntimeException("异常");
        }
        // 项目前端地址
        String proHomeUrl = "https://www.myserver.com/homePage";
        // --- LoginServiceImpl.java --- //
        HttpResponse ticketRes = HttpRequest.get("https://sso-server.com/checkTicket").form("ticket", ticket).execute();
        if (ObjectUtil.equals(ticketRes.getStatus(), HttpStatus.HTTP_OK)){
            // 1、获取sso-server相应结果 --> 有些是直接返回用户信息，也可能是用户的一个标识，还需要我们进一步处理
            String body = ticketRes.body();
            // 2、映射成实体类
            MyUser myUser = JSONUtil.toBean(body, MyUser.class);
            // 3、生成token给前端
            String cacheToken = IdUtil.fastSimpleUUID();
            String encryptToken = TokenUtil.encrypt(new LoginEncryptBO(cacheToken, System.currentTimeMillis()));
            // 4、缓存到Redis
            MyRedisUtil.set(CacheConstant.ACCESS_TOKEN_PREFIX + cacheToken, myUser, CacheConstant.EXPIRE_TIME);
            // 5、登录日志 等业务
            // ......
            LoginUserUtils.addCurrentUser(myUser);
            // 6、加到Cookie里面，这种登录的方式我认为存Cookie很不错
            ServletUtil.addCookie(response, TokenConstant.TOKEN_NAME, encryptToken);
            try {
                response.sendRedirect(proHomeUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 单点登陆
     * 情况2：sso服务端，先跳转到子系统的前端。前端把ticket再回传
     *
     * @param ticket   票据(一般来说都是给个ticket，最多就改个名字【比如：code】。当然可能会传其他的参数)
     */
    @RequestMapping("/login2")
    @ResponseBody
    public RespBean mySsoLogin(@RequestParam(value = "ticket") String ticket){
        if (StrUtil.isBlank(ticket)){
            // 抛个异常，记个日志。。。
            throw new RuntimeException("异常");
        }
        // --- LoginServiceImpl.java --- //
        HttpResponse ticketRes = HttpRequest.get("https://sso-server.com/checkTicket").form("ticket", ticket).execute();
        if (ObjectUtil.equals(ticketRes.getStatus(), HttpStatus.HTTP_OK)){
            // 1、获取sso-server相应结果 --> 有些是直接返回用户信息，也可能是用户的一个标识，还需要我们进一步处理
            String body = ticketRes.body();
            // 2、映射成实体类
            MyUser myUser = JSONUtil.toBean(body, MyUser.class);
            // 3、生成token给前端
            String cacheToken = IdUtil.fastSimpleUUID();
            String encryptToken = TokenUtil.encrypt(new LoginEncryptBO(cacheToken, System.currentTimeMillis()));
            // 4、缓存到Redis
            MyRedisUtil.set(CacheConstant.ACCESS_TOKEN_PREFIX + cacheToken, myUser, CacheConstant.EXPIRE_TIME);
            // 5、登录日志 等业务
            // ......
            LoginUserUtils.addCurrentUser(myUser);
            // 6、返回json给前端
            return RespBean.success(encryptToken);
        }
        return RespBean.error(RespBeanEnum.ERROR);
    }


}
