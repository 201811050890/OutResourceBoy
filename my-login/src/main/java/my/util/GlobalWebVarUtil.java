package my.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取Request
 * @author Brian Sun
 * @date 2023/07/07
 */
public class GlobalWebVarUtil {
    /**
     * 得到HttpServletRequest对象
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return requestAttributes != null ? ((ServletRequestAttributes) requestAttributes).getRequest() : null;
    }

    /**
     * 设置父线程requestAttributes共享 当异步执行的DAO方法需要记录日志时,需要先调用此方法设置
     */
    public static void setParentRequestShare() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            RequestContextHolder.setRequestAttributes(requestAttributes, true);
        }
    }

}
