package com.taoyuan.framework.bs.aspect;

import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Documented
public @interface OperControllerLog {
    String key() default "";
    String module() default "";
    String type() default "";
}
