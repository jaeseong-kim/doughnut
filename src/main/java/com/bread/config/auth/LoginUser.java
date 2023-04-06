package com.bread.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) //메소드 파라미터로 선언된 객체에서만 가능 primitive type은 안 되는 듯.
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {

}
