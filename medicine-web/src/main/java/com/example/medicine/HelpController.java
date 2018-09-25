package com.example.medicine;

import com.alibaba.fastjson.JSON;
import com.example.medicine.model.ActionOutput;
import com.example.medicine.model.ControllersOutput;
import com.example.medicine.model.EnumOutput;
import com.example.medicine.model.ParameterOutput;
import com.example.medicine.model.expands.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("help")
public class HelpController {
    final static String NAME_SPACE = "com.example.medicine.rest";

    private static String toUpperFirstChar(String string) {
        char[] charArray = string.toCharArray();
        charArray[0] += 32;
        return String.valueOf(charArray);
    }

    private static Type[] getParameterizedTypes(Field field) {
        Type genericType = field.getGenericType();
        if (genericType == null || !(genericType instanceof ParameterizedType)) {
            return null;
        }
        ParameterizedType pt = (ParameterizedType) genericType;
        Type[] types = pt.getActualTypeArguments();
        for (Type type : types) {
            if (type instanceof TypeVariable) {
                return null;
            }
        }
        return pt.getActualTypeArguments();
    }

    private static boolean isTypeVariable(Field field) {
        Type genericType = field.getGenericType();
        if (genericType == null || !(genericType instanceof ParameterizedType)) {
            return false;
        }
        ParameterizedType pt = (ParameterizedType) genericType;
        Type type = pt.getActualTypeArguments()[0];
        if (type instanceof TypeVariable) {
            return true;
        }
        return false;
    }

    private static Object returnBaseData(Class<?> clazz) {
        if (clazz.equals(String.class)) {
            return clazz.getSimpleName();
        }
        if (clazz.equals(Date.class)) {
            return new Date();
        }
        if (clazz.equals(Boolean.class)) {
            return false;
        }
        if (clazz.equals(Integer.class)) {
            return 1;
        }
        if (clazz.equals(Long.class)) {
            return 1L;
        }
        if (clazz.equals(Double.class)) {
            return 0.00d;
        }
        if (clazz.equals(BigDecimal.class)) {
            return new BigDecimal(0.00);
        }
        if (clazz.equals(Float.class)) {
            return 0.00f;
        }
        if (clazz.equals(Short.class)) {
            return Short.valueOf((short) 0);
        }
        if (clazz.equals(BigInteger.class)) {
            return BigInteger.ONE;
        }
        if (clazz.equals(Character.class)) {
            return new Character('a');
        }
        if (clazz.equals(Byte.class)) {
            return new Byte("0");
        }
        return null;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView help() {
        return new ModelAndView("help");
    }

    @RequestMapping(value = "controllers", method = RequestMethod.GET)
    public ModelAndView controllers() {
        List<Class<?>> classes = ReflectionUtil.getClasses(NAME_SPACE);
        List<ControllersOutput> list = classes.stream().map(p -> {
            ControllersOutput controllersOutput = new ControllersOutput(p.getName(), p.getSimpleName(), p.isAnnotationPresent(RequestMapping.class) ? p.getAnnotation(RequestMapping.class).value()[0] : null);
            if (p.isAnnotationPresent(ApiDescription.class)) {
                controllersOutput.setDescription(p.getAnnotation(ApiDescription.class).value());
            }
            return controllersOutput;
        }).collect(Collectors.toList());
        ModelAndView mav = new ModelAndView("controllers");
        mav.addObject("list", list);
        return mav;
    }

    @RequestMapping(value = "action", method = RequestMethod.GET)
    public ModelAndView action(@RequestParam String controller) throws Exception {
        Class<?> clazz = Class.forName(controller);
        String pathPrefix = clazz.getAnnotation(RequestMapping.class).value()[0];
        Method[] methods = clazz.getMethods();
        List<ActionOutput> list = new ArrayList<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);

                Class<?> outputClazz = method.getReturnType();
                if (method.getParameterTypes().length == 0) {
                    ActionOutput actionOutput = new ActionOutput(method.getName(), null, null
                            , outputClazz.getName(), outputClazz.getSimpleName(), null, getParameterFormat(outputClazz)
                            , pathPrefix + "/" + requestMapping.value()[0], requestMapping.produces()[0], requestMapping.method()[0].toString());
                    if (method.isAnnotationPresent(ApiDescription.class)) {
                        actionOutput.setDescription(method.getAnnotation(ApiDescription.class).value());
                    }

                    list.add(actionOutput);
                    continue;
                }

                Class<?> inputClazz = method.getParameterTypes()[0];

                ActionOutput actionOutput = new ActionOutput(method.getName(), inputClazz.getName(), inputClazz.getSimpleName()
                        , outputClazz.getName(), outputClazz.getSimpleName(), getParameterFormat(inputClazz), getParameterFormat(outputClazz)
                        , pathPrefix + "/" + requestMapping.value()[0], requestMapping.produces()[0], requestMapping.method()[0].toString());
                if (method.isAnnotationPresent(ApiDescription.class)) {
                    actionOutput.setDescription(method.getAnnotation(ApiDescription.class).value());
                }

                list.add(actionOutput);
            }
        }

        ModelAndView mav = new ModelAndView("action");
        mav.addObject("alist", list);
        return mav;
    }

    @RequestMapping(value = "parameter", method = RequestMethod.GET)
    public ModelAndView parameter(@RequestParam String type,
                                  @RequestParam(required = false) String genericType) throws Exception {
        Class<?> clazz;
        if (!StringUtils.isEmpty(type)) {
            clazz = Class.forName(type);
        } else {
            clazz = Class.forName(genericType);
        }
        Field[] fields = com.example.medicine.model.expands.ReflectionUtil.getClassFields(clazz);
        List<ParameterOutput> list = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(UserId.class) || field.isAnnotationPresent(CompanyId.class)) {
                continue;
            }

            Class<?> fieldType = field.getType();
            ParameterOutput parameterOutput = new ParameterOutput(field.getName(), fieldType.getSimpleName(), null, null, null, false);

            if (field.isAnnotationPresent(ApiDescription.class)) {
                parameterOutput.setDescription(field.getAnnotation(ApiDescription.class).value());
            }

//            if (field.isAnnotationPresent(SeeEnum.class)) {
//                SeeEnum seeEnum = field.getAnnotation(SeeEnum.class);
//                parameterOutput.setTypeName(fieldType.getSimpleName());
//                parameterOutput.setType(seeEnum.value().getName());
//                parameterOutput.setEnumeration(true);
//            }

            Type[] genericClazzs = getParameterizedTypes(field);
            Class<?> genericClazz = genericClazzs == null ? null : (Class<?>) genericClazzs[0];
            if (isTypeVariable(field) && !StringUtils.isEmpty(genericType)) {
                Class<?> genericClass = Class.forName(genericType);
                parameterOutput.setTypeName(fieldType.getSimpleName());
                parameterOutput.setType(genericClass.getName());
            }

            if (!ClassUtil.isBaseDataType(fieldType) && !Collection.class.isAssignableFrom(fieldType)) {
                parameterOutput.setTypeName(fieldType.getSimpleName());
                parameterOutput.setType(fieldType.getName());
            }

            if (genericClazz != null) {
                parameterOutput.setGenericName(genericClazz.getSimpleName());
                parameterOutput.setGenericType(genericClazz.getName());
            }

            list.add(parameterOutput);
        }

        List<EnumOutput> enums = getEnums("com.wuliujin.framework.core.layerComponentModels.PublicFailureReasons");

        ModelAndView mav = new ModelAndView("parameter");
        mav.addObject("list", list);
        mav.addObject("enums", enums);
        return mav;
    }

    @RequestMapping(value = "enumeration", method = RequestMethod.GET)
    public ModelAndView enumeration(@RequestParam String type) throws Exception {
        List<EnumOutput> enums = getEnums(type);
        List<EnumOutput> publicEnums = getEnums("com.wuliujin.framework.core.layerComponentModels.PublicFailureReasons");


        ModelAndView mav = new ModelAndView("enumeration");
        mav.addObject("enums", enums);
        mav.addObject("publicEnums", publicEnums);
        return mav;
    }

    private List<EnumOutput> getEnums(String type) throws Exception {
        Class<?> enumClazz = Class.forName(type);
        Method getIndex = enumClazz.getMethod("getIndex");
        Method getMessage = enumClazz.getMethod("getMessage");
        Object[] objs = enumClazz.getEnumConstants();
        List<EnumOutput> enums = new ArrayList<>();
        for (Object obj : objs) {
            enums.add(new EnumOutput(obj.toString(), (int) getIndex.invoke(obj), getMessage.invoke(obj).toString()));
        }
        return enums;
    }

    private String getParameterFormat(Class<?> clazz) throws Exception {
        return JSON.toJSONString(getParameterValue(clazz, null, 0));
    }

    private Object getParameterValue(Class<?> clazz, Class<?> genericClazz, int numebr) throws Exception {
        if (clazz.equals(Object.class)) {
            return null;
        }
        Object model;
        model = clazz.newInstance();

        Field[] fields = com.example.medicine.model.expands.ReflectionUtil.getClassFields(clazz);
        for (Field field : fields) {
            if (field.isAnnotationPresent(UserId.class) || field.isAnnotationPresent(CompanyId.class)) {
                continue;
            }

            String fieldName = field.getName();
            Class<?> propertyType = field.getType();
            if ((propertyType.equals(boolean.class) || propertyType.equals(Boolean.class)) && fieldName.startsWith("is")) {
                fieldName = toUpperFirstChar(fieldName.replaceFirst("is", ""));
            }

            PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);
            if (!propertyType.isPrimitive()) {
                Method getMethod = pd.getWriteMethod();
                getMethod.invoke(model, getAttributeValue(clazz, field, genericClazz, numebr));
            }
        }

        return model;
    }

    private Object getAttributeValue(Class<?> classClazz, Field field, Class<?> parentClazz, int number) throws Exception {
        Class<?> clazz = field.getType();
        Object baseData = returnBaseData(clazz);
        if (baseData != null) {
            return baseData;
        }

        Type[] genericClazzs = getParameterizedTypes(field);
        Class<?> genericClazz = genericClazzs == null ? null : (Class<?>) genericClazzs[0];
        if (clazz.equals(List.class)) {
            List<Object> list = new ArrayList<>();
            if (genericClazz != null) {
                Object genericObj = returnBaseData(genericClazz);
                if (genericObj != null) {
                    list.add(genericObj);
                    return list;
                }

                if (Objects.equals(classClazz, genericClazz)) {
                    number++;
                }
                if (number < 2) {
                    list.add(getParameterValue(genericClazz, null, number));
                }
                return list;
            }

            if (parentClazz != null) {
                list.add(getParameterValue(parentClazz, null, number));
                return list;
            }

            list.add("object");
            return list;
        }
        if (clazz.equals(Map.class)) {
            Map<Object, Object> map = new HashMap<>();
            if (genericClazzs == null) {
                map.put("object", "object");
                return map;
            }
            Class<?> keyClazz = (Class<?>) genericClazzs[0];
            Class<?> valueClazz = (Class<?>) genericClazzs[1];
            Object key = returnBaseData(keyClazz);
            if (key == null) {
                key = getParameterValue(keyClazz, null, number);
            }
            Object value = returnBaseData(valueClazz);
            if (value == null) {
                value = getParameterValue(valueClazz, null, number);
            }

            map.put(key, value);
            return map;
        }
        return getParameterValue(clazz, genericClazz, number);
    }
}
