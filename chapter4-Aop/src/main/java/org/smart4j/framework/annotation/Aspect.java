package org.smart4j.framework.annotation;

import java.lang.annotation.*;

/**
 * Created by bing on 1/4/16.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    Class<? extends Annotation> value();
}
