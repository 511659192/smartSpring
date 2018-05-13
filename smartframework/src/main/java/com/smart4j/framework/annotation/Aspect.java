package com.smart4j.framework.annotation;

import java.lang.annotation.*;

/**
 * Created by ym on 2018/5/13.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    Class<? extends Annotation> value();
}
