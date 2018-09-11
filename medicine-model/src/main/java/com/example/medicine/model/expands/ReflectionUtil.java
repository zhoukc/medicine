package com.example.medicine.model.expands;

import java.lang.reflect.Field;

public class ReflectionUtil {


    public static Field[] getClassFields(Class clazz, boolean isFather) {
        if (clazz.equals(Object.class))
            return null;
        Field[] declaredFields = clazz.getDeclaredFields();
        if (isFather) {
            Field[] afterFields = getClassFields(clazz.getSuperclass(), isFather);
            if (afterFields == null)
                return declaredFields;

            return ArrayUtil.joinFields(afterFields, declaredFields);
        }
        return declaredFields;
    }

    public static Field[] getClassFields(Class clazz) {
        return getClassFields(clazz, true);
    }
}
