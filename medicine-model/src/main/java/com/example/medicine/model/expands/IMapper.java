package com.example.medicine.model.expands;

public  interface IMapper
{
   <O, T> T map(O paramO, Class<T> paramClass);

   <O, T> T map(O paramO, T paramT);

   <A, B> void register(Class<A> paramClass, Class<B> paramClass1);

   <A, B> void register(Class<A> paramClass, Class<B> paramClass1, String[] paramArrayOfString);
}