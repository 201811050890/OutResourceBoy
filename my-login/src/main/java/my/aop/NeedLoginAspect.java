package my.aop;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import my.domain.LoginEncryptBO;
import my.util.GlobalWebVarUtil;
import my.util.LoginUserUtils;
import my.redis.MyRedisUtil;
import my.constant.TokenConstant;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import result.RespBean;
import result.RespBeanEnum;
import result.ServiceException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Sun
 * @date 2023/8/18 17:34
 */
@Aspect
@Component
@Slf4j
public class NeedLoginAspect {

    @Pointcut("@within(my.aop.NeedLogin) || @annotation(my.aop.NeedLogin)")
    public void needLoginPoint() {
    }

    @Before(value = "needLoginPoint() ")
    public void needLogin(){
        HttpServletRequest request = GlobalWebVarUtil.getRequest();
        LoginEncryptBO loginToken = LoginUserUtils.getCacheTokenByRequest(request);
        if (ObjectUtil.isNull(loginToken)){
           throw new ServiceException(RespBean.error(RespBeanEnum.NOT_LOGIN));
        }
        String token = loginToken.getToken();
        String cacheTokenKey = TokenConstant.TOKEN_PREFIX + token;
        Object o = MyRedisUtil.get(cacheTokenKey);
        if (ObjectUtil.isNull(o)){
            throw new ServiceException(RespBean.error(RespBeanEnum.NOT_LOGIN));
        }
    }
}
