package com.example.medicine.common.mapper;

import com.example.medicine.common.lambda.IAction;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.beans.BeanCopier;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description cglib对象浅拷贝
 * create by zhoukc
 */
@Slf4j
public class CGlibMapper {

    private static final ConcurrentHashMap<String, BeanCopier> mapCaches = new ConcurrentHashMap<>();

    public static <O, T> T mapper(O source, Class<T> target) {
        T instance = baseMapper(source, target);
        return instance;
    }

    public static <O, T> T mapper(O source, Class<T> target, IAction<T> action) {
        T instance = baseMapper(source, target);
        action.run(instance);
        return instance;
    }

    public static <O, T> T mapperObject(O source, T target) {
        String baseKey = generateKey(source.getClass(), target.getClass());
        BeanCopier copier;
        if (!mapCaches.containsKey(baseKey)) {
            copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            mapCaches.put(baseKey, copier);
        } else {
            copier = mapCaches.get(baseKey);
        }
        copier.copy(source, target, null);
        return target;
    }

    public static <O, T> T mapperObject(O source, T target, IAction<T> action) {
        String baseKey = generateKey(source.getClass(), target.getClass());
        BeanCopier copier;
        if (!mapCaches.containsKey(baseKey)) {
            copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            mapCaches.put(baseKey, copier);
        } else {
            copier = mapCaches.get(baseKey);
        }
        copier.copy(source, target, null);
        action.run(target);
        return target;
    }

    private static <O, T> T baseMapper(O source, Class<T> target) {
        String baseKey = generateKey(source.getClass(), target);
        BeanCopier copier;
        if (!mapCaches.containsKey(baseKey)) {
            copier = BeanCopier.create(source.getClass(), target, false);
            mapCaches.put(baseKey, copier);
        } else {
            copier = mapCaches.get(baseKey);
        }
        T instance = null;
        try {
            instance = target.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.error("mapper 创建对象异常" + e.getMessage());
        }
        copier.copy(source, instance, null);
        return instance;
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }
}
