package org.smart4j.chapter1.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by bing on 11/9/15.
 */
public final class CollectionUtil {
    public static boolean isEmpty( Collection<?> collection ) {
        return CollectionUtils.isEmpty( collection );
    }
    public static boolean isNotEmpty( Collection<?> collection ) {
        return CollectionUtils.isNotEmpty( collection );
    }

    public static boolean isEmpty( Map<?, ?> map ){
        return MapUtils.isEmpty( map );
    }

    public static boolean isNotEmpty( Map<?, ?> map ){
        return !isEmpty( map );
    }
}
