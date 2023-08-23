package result;

/**
 * 公共返回对象枚举
 *
 * @author OutResource Boy
 * @date 2022/3/2 1:44 下午
 */

public enum RespBeanEnum {

    //通用
    SUCCESS(200, "SUCCESS"),
    ERROR(500, "服务端异常"),

    //500XXX登录模块
    LOGIN_ERROR(500210, "用户名或者密码不正确"),
    MOBILE_ERROR(500211, "手机号码格式不正确"),
    BIND_ERROR(500212, "参数校验异常"),
    MOBILE_NOT_EXIST(500213, "手机号码不存在"),
    PASSWORD_UPDATE_FAIL(500214, "更新密码失败"),
    SESSION_ERROR(500215, "用户SESSION不存在"),
    USER_NULL(500216, "用户不存在"),
    PASSWORD_ERROR(500217, "密码错误"),
    SECOND_PASS_ERROR(500218, "两次密码输入不同"),
    EMAIL_ERROR(500219, "邮件发送失败，请检查邮件地址是否正确，如无误请联系客服"),
    EMAIL_CODE_ERROR(500220, "验证码输入错误"),
    EMAIL_CODE_EXPIRED(500221, "验证码已过期，请重新获取"),
    USER_LOCKED(500222, "用户已锁定"),
    PASSWORD_SAME_AS_OLD(500223, "与旧密码相同"),
    PASSWORD_TOO_SHORT(500224, "密码长度不可小于6位"),
    DUPLICATE_USER(500225, "当前邮箱已注册"),
    IP_CLOCKED(500226, "IP已锁定"),
    USER_REGISTERED(500227, "该账号已注册"),
    NOT_LOGIN(500228, "账户未登录或登录已过期"),
    NO_ROLES(500229, "无权访问"),


    CREATE_USER_ERROR(500230, "用户创建失败"),
    INTERVIEW_ID_NOT_FOUND(500231, "无法找到当前题目信息"),
    NO_VERSION_CACHE_IN_REDIS(500232, "缓存中没有上一版的记录信息"),
    FAVOURITE_IS_EXISTING(500233, "当前问题已经添加到我的收藏中");

    private final Integer code;
    private final String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    RespBeanEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
