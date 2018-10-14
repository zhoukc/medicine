package com.example.medicine.common.lambda;


@FunctionalInterface
public interface IAction<T> {
    void run(T param);
}
