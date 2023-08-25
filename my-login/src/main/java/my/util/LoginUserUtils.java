package my.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import my.constant.TokenConstant;
import my.domain.LoginEncryptBO;
import my.domain.MyUser;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * @author Brian Sun
 * @date 2023/8/18 16:52
 */
public class LoginUserUtils {
    /**
     * 保存用户对象的ThreadLocal
     */
    private static final ThreadLocal<MyUser> USER_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 添加当前登录用户方法
     */
    public static void addCurrentUser(MyUser user) {
        USER_THREAD_LOCAL.set(user);
    }

    /**
     * 获取当前用户
     *
     * @return {@link MyUser}
     */
    public static MyUser getCurrentUser() {
        MyUser sysUserRole = USER_THREAD_LOCAL.get();
        if (ObjectUtil.isNull(sysUserRole)){
            sysUserRole = new MyUser();
            sysUserRole.setId("-1");
            sysUserRole.setRoleList(new ArrayList<>());
        }
        return sysUserRole;
    }

    /**
     * 防止内存泄漏
     */
    public static void remove() {
        USER_THREAD_LOCAL.remove();
    }

    public static MyUser getDefaultUser(){
        MyUser sysUserRole = new MyUser();
        sysUserRole.setId("-1");
        sysUserRole.setUserName("anonymous");
        sysUserRole.setRoleList(new ArrayList<>());
        return sysUserRole;
    }

    public static LoginEncryptBO getCacheTokenByRequest(HttpServletRequest request) {
        // 1、从Header头获取
        String token = null;

        token = ServletUtil.getHeader(request, TokenConstant.TOKEN_NAME, StandardCharsets.UTF_8);
        LoginEncryptBO decode = null;

        if (StrUtil.isEmpty(token)) {
            // 2、从Cookie获取
            Cookie cookie = ServletUtil.getCookie(request, TokenConstant.TOKEN_NAME);
            token = cookie.getValue();
        }
        decode = TokenUtil.decode(token);
        return decode;
    }
}
