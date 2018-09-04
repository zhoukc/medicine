package com.example.medicine.expands;

@FunctionalInterface
public  interface IAction<T>
{
    void run(T paramT);
}