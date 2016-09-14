package com.yihu.wlyy.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import java.util.Collection;

public class CollectionUtil {

    public static Logger logger = Logger.getLogger(CollectionUtil.class);
    public static Boolean isEmpty(Collection collection) {
        return CollectionUtils.isEmpty(collection);
    }

    public static Boolean isNotEmpty(Collection collection) {
        return CollectionUtils.isNotEmpty(collection);
    }
}
