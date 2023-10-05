package my.aop;

import java.lang.annotation.*;

/**
 * @author Brian Sun
 * @date 2023/8/18 17:34
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NeedLogin {

}
