package log;

import java.lang.annotation.*;

/**
 * @author OutResource Boy
 * @date 2023/8/17 22:43
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpLog {
    /**
     * 操作模块
     */
    String module() default "";
    /**
     * 操作标题
     */
    String title() default "";

    /**
     * 是否使用了body参数
     */
    boolean useBody() default false;
    /**
     * 注解：body参数位于第几位
     */
    int bodyIndex() default 0;



}
