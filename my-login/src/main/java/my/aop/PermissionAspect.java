package my.aop;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import my.constant.TokenConstant;
import my.domain.LoginEncryptBO;
import my.domain.MyUser;
import my.redis.MyRedisUtil;
import my.util.GlobalWebVarUtil;
import my.util.LoginUserUtils;
import my.util.TokenUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import result.RespBean;
import result.RespBeanEnum;
import result.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Sun
 * @date 2023/8/18 17:34
 */
@Component
@Aspect
public class PermissionAspect {
    @Pointcut("@annotation(my.aop.Permission)")
    public void permissionPoint() {
    }

    @Before(value = "permissionPoint()")
    public void hasPermission(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        Permission permission = method.getAnnotation(Permission.class);

        MyUser currentUser = LoginUserUtils.getCurrentUser();
        String[] roles = permission.roles();
        ArrayList<String> permRoles = ListUtil.toList(roles);
        List<String> loginUserRoles = currentUser.getRoleList();
        if (CollectionUtil.isEmpty(permRoles)){

        }

    }
}
