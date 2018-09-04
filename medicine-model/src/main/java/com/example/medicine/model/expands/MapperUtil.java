package com.example.medicine.model.expands;


import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


public class MapperUtil {
    private static final IMapper mapperAgent = new Mapper();
    private static final Set<String> mapCaches = new HashSet();

//  public MapperUtil(IMapper mapperAgent)
//  {
//    mapperAgent = mapperAgent;
//  }

    public static <O, T> T mapper(O obj, Class<T> val) {
        String key = obj.getClass().getName() + "_" + val.getName();
        if (mapCaches.contains(key))
            return mapperAgent.map(obj, val);

        mapperRegister(obj.getClass(), val);
        mapCaches.add(key);
        return mapperAgent.map(obj, val);
    }

//  public static <O, T> T mapper(O obj, Class<T> val) {
//    T valObject = mapper(obj, val);
//    return valObject;
//  }

    public static <O, T> T mapperObject(O var1, T var2) {
        String key = var1.getClass().getName() + "_" + var2.getClass().getName();
        if (mapCaches.contains(key))
            return mapperAgent.map(var1, var2);

        mapperRegister(var1.getClass(), var2.getClass());
        mapCaches.add(key);
        return mapperAgent.map(var1, var2);
    }

//  public static <O, T> T mapperObject(O var1, T var2) {
//    T valObject = mapperObject(var1, var2);
//    return valObject;
//  }

    public static Type[] getParameterizedTypes(Object object) {
        Type superclassType = object.getClass().getGenericSuperclass();
        if (!(ParameterizedType.class.isAssignableFrom(superclassType.getClass())))
            return null;

        return ((ParameterizedType) superclassType).getActualTypeArguments();
    }

    private static void mapperRegister(Class<?> sourceClazz, Class<?> targetClazz) {
        if ((Collection.class.isAssignableFrom(sourceClazz)) || (Map.class.isAssignableFrom(sourceClazz)) || (Iterator.class.isAssignableFrom(sourceClazz))) {
            return;
        }

        Field[] fields = getClassFields(sourceClazz);
        Field[] targetFields = getClassFields(targetClazz);

        List excludes = new ArrayList();
        Map targetNames = fieldsToMap(targetFields);
        Field[] arrayOfField1 = fields;
        int i = arrayOfField1.length;
        label234:
        for (int j = 0; j < i; ++j) {
            Field field = arrayOfField1[j];
            Class clazz = field.getType();
            if (ClassUtil.isBaseDataType(clazz))
                break label234;

            String fieldName = field.getName();
            if (!(targetNames.containsKey(fieldName))) {
                break label234;
            }

            Class fieldType = field.getType();
            if ((((Collection.class.isAssignableFrom(fieldType)) || (Map.class.isAssignableFrom(fieldType)) || (Iterator.class.isAssignableFrom(fieldType)))) &&
                    (!(isEqualType(field,
                            (Field) targetNames.get
                                    (fieldName))))) {
                excludes.add(fieldName);
            }

            Class tClazz = ((Field) targetNames.get(fieldName)).getType();
            if (Objects.equals(tClazz, clazz))
                mapperAgent.register(clazz, tClazz);
            //mapperRegister(clazz, tClazz);
        }
        mapperAgent.register(sourceClazz, targetClazz, (String[]) excludes.toArray(new String[excludes.size()]));
    }

    private static void attributeRegister(Class<?> clazz, Class<?> tClazz) {
        if (clazz.isAssignableFrom(Collection.class)) {
            Type[] types = getParameterizedTypes(clazz);
            if (types == null)
                return;
            mapperRegister(types[0].getClass(), getParameterizedTypes(tClazz)[0].getClass());
        } else {
            mapperAgent.register(clazz, tClazz);
        }
    }

    private static HashMap<String, Field> fieldsToMap(Field[] targetFields) {
        HashMap map = new HashMap();
        Field[] arrayOfField = targetFields;
        int i = arrayOfField.length;
        for (int j = 0; j < i; ++j) {
            Field field = arrayOfField[j];
            map.put(field.getName(), field);
        }
        return map;
    }

    private static Field[] getClassFields(Class clazz) {
        if ((clazz == null) || (clazz.equals(Object.class)))
            return null;
        Field[] declaredFields = clazz.getDeclaredFields();
        Field[] afterFields = getClassFields(clazz.getSuperclass());
        if (afterFields == null) {
            return declaredFields;
        }

        return ArrayUtil.joinFields(afterFields, declaredFields);
    }

    private static boolean isEqualType(Field fieldInput, Field fieldOutput) {
        return Objects.equals(fieldInput.getGenericType(), fieldOutput.getGenericType());
    }
}