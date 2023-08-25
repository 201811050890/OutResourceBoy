package my.aop;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import my.constant.RoleConcatConstant;
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
import java.util.HashSet;
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

    /**
     * 判断用户是否有权限
     * <p>
     *     这里用户是否登录未做判断
     * </p>
     * @param joinPoint 切点
     */
    @Before(value = "permissionPoint()")
    public void hasPermission(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        Permission permission = method.getAnnotation(Permission.class);
        String[] roles = permission.roles();
        ArrayList<String> permRoles = ListUtil.toList(roles);
        if (CollectionUtil.isEmpty(permRoles)){
            return;
        }
        MyUser currentUser = LoginUserUtils.getCurrentUser();
        List<String> loginUserRoles = currentUser.getRoleList();

        RoleConcatConstant concat = permission.concat();
        judgeRoles(concat, permRoles, loginUserRoles);
    }

    /**
     * 判断权限
     * @param concat 权限之间的连接情况【OR或AND】
     * @param permRoles 当前方法所需的Role
     * @param loginUserRoles 当前登录用户拥有的Role
     */
    private void judgeRoles(RoleConcatConstant concat, ArrayList<String> permRoles, List<String> loginUserRoles) {
        HashSet<String> permRoleSet = new HashSet<>(permRoles);
        if (ObjectUtil.equal(concat, RoleConcatConstant.OR)){
            boolean b = permRoleSet.stream().anyMatch(loginUserRoles::contains);
            if (!b){
                throw new ServiceException(RespBean.error(RespBeanEnum.NO_ROLES));
            }
        }else if(ObjectUtil.equal(concat, RoleConcatConstant.AND)){
            boolean b = new HashSet<>(loginUserRoles).containsAll(permRoleSet);
            if (!b){
                throw new ServiceException(RespBean.error(RespBeanEnum.NO_ROLES));
            }
        }
    }
}
