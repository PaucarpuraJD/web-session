package org.jotad.apiservlet.weapp.header.Interceptor;

import jakarta.interceptor.InterceptorBinding;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, TYPE})
public @interface TransactionalJpa {
}
