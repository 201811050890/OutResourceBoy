package my.util;

/**
 * @author OutResource Boy
 * @date 2023/8/4 12:58
 */
public class CacheConstant {
    /**
     * token 前缀
     */
    public static String ACCESS_TOKEN_PREFIX = "ACCESS:TOKEN:";

    /**
     * 过期时间
     */
    public static Long EXPIRE_TIME = 60 * 60 * 12L;

    /**
     * 版本记录前缀
     */
    public static String ISSUE_VERSION_CACHE_PREFIX = "ISSUE:VERSION:CACHE:";

    /**
     * 版本记录过期时间
     */
    public static Long ISSUE_VERSION_EXPIRE_TIME = 60 * 60 * 10L;
}
