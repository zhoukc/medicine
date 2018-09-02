package com.example.medicine.config;

import java.lang.annotation.*;

/**
 * @author fang
 *         Created by Fang on 2017/12/19.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ChooseDataSource {
    DataSources value() default DataSources.MEDICINE;
}
