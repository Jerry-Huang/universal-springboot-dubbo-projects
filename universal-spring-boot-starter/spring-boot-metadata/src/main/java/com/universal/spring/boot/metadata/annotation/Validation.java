package com.universal.spring.boot.metadata.annotation;


import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Validation {

    @AliasFor("rules")
    String[] value() default {};

    @AliasFor("value")
    String[] rules() default {};
}
