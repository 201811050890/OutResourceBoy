package my.util;

import cn.hutool.core.util.ObjectUtil;
import my.domain.MyUser;

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
        sysUserRole.setUserName("admin");
        return sysUserRole;
    }
}
