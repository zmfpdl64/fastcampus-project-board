package com.fastcampus.fastcampusprojectboard.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface TestComponent {

    /**DDD
     * The value may indicate a suggestion for a logical component name, to be turned into
     * a Spring bean in case of an auto-detected component.
     * @return the specified bean name, if any
     */
    @AliasFor(annotation = Component.class)
    String value() default "";

}