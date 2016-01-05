package org.smart4j.framework;

import org.smart4j.framework.helper.*;
import org.smart4j.framework.util.ClassUtil;

/**
 * Created by bing on 11/26/15.
 */
public final class HelperLoader {

    public static void init(){
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for(Class<?> cls : classList){
            ClassUtil.loadClass(cls.getName(), true);
        }
    }
}
