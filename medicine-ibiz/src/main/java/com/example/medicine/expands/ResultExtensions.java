package com.example.medicine.expands;


import com.example.medicine.model.expands.IContent;
import com.example.medicine.model.expands.IFailureReason;
import com.example.medicine.model.expands.IResult;
import com.example.medicine.model.expands.MapperUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Slf4j
public class ResultExtensions {
    public static boolean IsSucceeded(IResult result) {
        return (result.getResultStates() == ResultStates.SUCCEEDED.getIndex());
    }

    public static <TResult extends IResult> TResult ToSucceededResult(Class<TResult> clazz) {
        TResult
                result = ToResult(null, ResultStates.SUCCEEDED.getIndex(), null, clazz, null);
        return result;
    }

    public static <TResult extends IResult> TResult ToSucceededResult(Object source, Class<TResult> clazz) {
        TResult result = ToResult(source, ResultStates.SUCCEEDED.getIndex(), null, clazz, null);
        return result;
    }

    public static <TResult extends IResult> TResult ToSucceededResult(Object source, Class<TResult> clazz, IAction<Object> action) {
        TResult result = ToResult(source, ResultStates.SUCCEEDED.getIndex(), null, clazz, action);
        return result;
    }

    public static <TResult extends IResult> TResult ToFailedResult(Enum failureReason, Class<TResult> clazz) {
        TResult result = ToResult(null, ResultStates.FAILED.getIndex(), failureReason, clazz, null);
        return result;
    }

    public static <TResult extends IResult> TResult ToFailedResult(Enum failureReason, Object source, Class<TResult> clazz) {
        TResult result = ToResult(source, ResultStates.FAILED.getIndex(), failureReason, clazz, null);
        return result;
    }

    private static <TResult extends IResult> TResult ToResult(Object source, int state, Enum failureReason, Class<TResult> clazz, IAction<Object> action) {
        Object content = null;
        TResult result = null;
        try {
            result = (TResult) clazz.newInstance();
        } catch (Exception ex) {
            return null;
        }
        result.setResultStates(state);
        if ((failureReason != null) && (result instanceof IFailureReason)) {
            Class<? extends Enum> failureReasonClass = failureReason.getClass();
            if (!failureReasonClass.isEnum()) {
                throw new RuntimeException("参数错误，必须为enum类型");
            }

            Enum[] enums = failureReasonClass.getEnumConstants();
            Integer code = 0;
            String message = null;
            try {
                Enum obj = enums[0];
                Field field = failureReasonClass.getDeclaredField("index");
                field.setAccessible(true);
                code = (Integer) field.get(obj);

                Field field2 = failureReasonClass.getDeclaredField("message");
                field2.setAccessible(true);
                message = String.valueOf(field2.get(obj));

            } catch (NoSuchFieldException e) {
                log.error("必须enum有index和message");
            } catch (Exception e) {
                log.error("方法拒绝访问");
            }
            ((IFailureReason) result).setCode(code);
            ((IFailureReason) result).setMessage(message);
        }
        if ((source == null) || (!(result instanceof IContent)))
            return result;

        IContent contentResult = (IContent) result;

        Method contentClazz = null;
        try {
            contentClazz = clazz.getMethod("getContent", new Class[0]);
        } catch (Exception ex) {
            return result;
        }

        if (contentClazz.getReturnType().isInstance(source))
            content = source;
        else
            content = MapperUtil.mapper(source, contentClazz.getReturnType());

        if (action != null)
            action.run(content);
        contentResult.setContent(content);
        return result;
    }
}
