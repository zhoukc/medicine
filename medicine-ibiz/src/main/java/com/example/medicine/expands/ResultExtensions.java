package com.example.medicine.expands;


import com.example.medicine.model.expands.IContent;
import com.example.medicine.model.expands.IFailureReason;
import com.example.medicine.model.expands.IResult;
import com.example.medicine.model.expands.MapperUtil;

import java.lang.reflect.Method;

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

    public static <TResult extends IResult> TResult ToFailedResult(int failureReason, Class<TResult> clazz) {
        TResult result = ToResult(null, ResultStates.FAILED.getIndex(), Integer.valueOf(failureReason), clazz, null);
        return result;
    }

    public static <TResult extends IResult> TResult ToFailedResult(int failureReason, Object source, Class<TResult> clazz) {
        TResult result = ToResult(source, ResultStates.FAILED.getIndex(), Integer.valueOf(failureReason), clazz, null);
        return result;
    }

    private static <TResult extends IResult> TResult ToResult(Object source, int state, Integer failureReason, Class<TResult> clazz, IAction<Object> action) {
        Object content = null;
        TResult result = null;
        try {
            result = (TResult) clazz.newInstance();
        } catch (Exception ex) {
            return null;
        }
        result.setResultStates(state);
        if ((failureReason != null) && (result instanceof IFailureReason))
            ((IFailureReason) result).setFailureReason(failureReason);

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