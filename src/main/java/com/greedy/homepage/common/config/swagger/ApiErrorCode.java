package com.greedy.homepage.common.config.swagger;

import com.greedy.homepage.common.exception.FailMessage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ApiErrorCodes.class)
public @interface ApiErrorCode {

    FailMessage[] value();
}
