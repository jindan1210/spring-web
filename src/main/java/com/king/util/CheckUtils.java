package com.king.util;

import org.apache.commons.beanutils.BeanPredicate;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Created by 金丹 on 2017/12/22.
 */
public class CheckUtils {
    public CheckUtils() {
    }

    public static void valueIsEmpty(String str, String checkName) {
        if(StringUtils.isBlank(str)) {
            StringBuffer sb = new StringBuffer();
            sb.append(checkName).append(" must be specified");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public static void valueIsEmpty(String[] strs, String... checkNames) {
        StringBuffer buffer = new StringBuffer();
        if(strs.length != checkNames.length) {
            throw new IllegalArgumentException("strs's length is not equlas checkNames's length");
        } else {
            for(int i = 0; i < strs.length; ++i) {
                if(StringUtils.isBlank(strs[i])) {
                    buffer.append(checkNames[i]).append(",");
                }
            }

            if(buffer.toString().endsWith(",")) {
                buffer.append(" must be specified");
                throw new IllegalArgumentException(buffer.toString());
            }
        }
    }

    public static void valueIsNull(Object obj, String checkName) {
        StringBuffer sb = new StringBuffer();
        checkValueIsNull(sb, obj, checkName);
        if(sb.toString().endsWith(",")) {
            sb.append(" must be specified");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public static void valueIsNull(Object[] objs, String[] checkNames) {
        if(objs.length != checkNames.length) {
            throw new IllegalArgumentException("objs's length is not equlas checkNames's length");
        } else {
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < objs.length; ++i) {
                checkValueIsNull(sb, objs[i], checkNames[i]);
            }

            if(sb.toString().endsWith(",")) {
                sb.append(" must be specified");
                throw new IllegalArgumentException(sb.toString());
            }
        }
    }

    private static void checkValueIsNull(StringBuffer sb, Object obj, String checkNames) {
        if(obj == null) {
            sb.append(checkNames + ",");
        } else {
            if(!isPrimitive(obj) && !(obj instanceof Date) && !(obj instanceof String) && !(obj instanceof BigDecimal)) {
                String[] chkname = checkNames.split(",");

                for(int j = 0; j < chkname.length; ++j) {
                    Predicate predicate = new BeanPredicate(chkname[j], PredicateUtils.nullPredicate());
                    if(predicate.evaluate(obj)) {
                        sb.append(chkname[j] + ",");
                    }
                }
            }

        }
    }

    public static boolean isPrimitive(Object obj) {
        return obj.getClass().isPrimitive()?true:obj instanceof Long || obj instanceof Integer || obj instanceof Float || obj instanceof Boolean || obj instanceof Double;
    }

    public static void notNull(Object obj, String message) {
        if(obj == null) {
            throw new IllegalArgumentException(message + " must be specified");
        }
    }

    public static void notNull(Object[] objs, String... messages) {
        if(objs.length != messages.length) {
            throw new IllegalArgumentException("objs's length is not equlas message's length");
        } else {
            for(int i = 0; i < objs.length; ++i) {
                if(objs[i] == null) {
                    throw new IllegalArgumentException(messages[i] + " must be specified");
                }
            }

        }
    }

    public static void strNotNull(Object obj, String message) {
        if(obj == null) {
            throw new IllegalArgumentException(message + " must be specified");
        } else if(obj instanceof String && obj.toString().trim().length() == 0) {
            throw new IllegalArgumentException(message + " must be specified");
        }
    }

    public static void notEmpty(Object obj, String message) {
        if(obj == null) {
            throw new IllegalArgumentException(message + " must be specified");
        } else if(obj instanceof String && obj.toString().trim().length() == 0) {
            throw new IllegalArgumentException(message + " must be specified");
        } else if(obj.getClass().isArray() && Array.getLength(obj) == 0) {
            throw new IllegalArgumentException(message + " must be specified");
        } else if(obj instanceof Collection && ((Collection)obj).isEmpty()) {
            throw new IllegalArgumentException(message + " must be specified");
        } else if(obj instanceof Map && ((Map)obj).isEmpty()) {
            throw new IllegalArgumentException(message + " must be specified");
        }
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isEmpty(Object obj) {
        return obj == null?true:(obj instanceof String && obj.toString().trim().length() == 0?true:(obj.getClass().isArray() && Array.getLength(obj) == 0?true:(obj instanceof Collection && ((Collection)obj).isEmpty()?true:obj instanceof Map && ((Map)obj).isEmpty())));
    }
}

