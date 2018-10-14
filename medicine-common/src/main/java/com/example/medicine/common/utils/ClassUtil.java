package com.example.medicine.common.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class ClassUtil {
    public static boolean isJavaClass(Class<?> clz) {
        return ((clz != null) && (clz.getClassLoader() == null));
    }

    public static boolean isBaseDataType(Class clazz) {
        return ((clazz.isPrimitive()) ||
                (clazz.equals
                        (String.class)) ||
                (clazz.equals
                        (Date.class)) ||
                (clazz.equals
                        (Boolean.class)) ||
                (clazz.equals
                        (Integer.class)) ||
                (clazz.equals
                        (Long.class)) ||
                (clazz.equals
                        (Double.class)) ||
                (clazz.equals
                        (BigDecimal.class)) ||
                (clazz.equals
                        (Float.class)) ||
                (clazz.equals
                        (Short.class)) ||
                (clazz.equals
                        (BigInteger.class)) ||
                (clazz.equals
                        (Character.class)) ||
                (clazz.equals
                        (Byte.class)));
    }

    public static Type[] getParameterizedTypes(Class<?> clazz) {
        Type superclassType = clazz.getGenericSuperclass();
        if (!(ParameterizedType.class.isAssignableFrom(superclassType.getClass())))
            return null;

        return ((ParameterizedType) superclassType).getActualTypeArguments();
    }

    public static Type[] getParameterizedTypes(Object object) {
        return getParameterizedTypes(object.getClass());
    }
}