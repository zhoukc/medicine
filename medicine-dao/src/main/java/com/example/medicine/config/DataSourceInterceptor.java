package com.example.medicine.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author zhou
 */
@Component
@Aspect
@Order(0)
public class DataSourceInterceptor {
    @Pointcut(value = "@annotation(com.example.medicine.config.ChooseDataSource)")
    private void pointcut() {
    }

    /**
     * 环绕通知需要携带ProceedingJoinPoint 这个类型的参数
     * 环绕通知类似于动态代理的全过程 ProceedingJoinPoint类型的参数可以决定是否执行目标函数
     * 环绕通知必须有返回值
     *
     * @param proceedingJoinPoint
     * @return
     */
    @Around(value = "pointcut() && @annotation(chooseDataSource)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, ChooseDataSource chooseDataSource) throws Throwable {
        DataSourceTypeManager.set(chooseDataSource.value());
        return proceedingJoinPoint.proceed();
    }
}
