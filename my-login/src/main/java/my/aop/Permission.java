package my.aop;

import my.constant.RoleConcatConstant;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.annotation.*;

/**
 * @author Brian Sun
 * @date 2023/8/18 17:33
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {
    /**
     * 角色列表，当不配置任何时，默认为空
     */
    String[] roles() default {};

    /**
     * AND 表示角色列表中的每个角色都需要，OR表示只要有其中一个即可
     * 默认为 OR
     */
    RoleConcatConstant concat() default RoleConcatConstant.OR;


}
