package com.example.medicine.model.expands;

import com.baidu.unbiz.easymapper.MapperFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author zhoukc
 */

public class Mapper implements IMapper {
    @Override
    public <O, T> T map(O obj, Class<T> val) {
        return MapperFactory.getCopyByRefMapper().map(obj, val);
    }

    @Override
    public <O, T> T map(O var1, T var2) {
        return MapperFactory.getCopyByRefMapper().map(var1, var2);
    }

    @Override
    public <A, B> void register(Class<A> var1, Class<B> var2) {
        MapperFactory.getCopyByRefMapper().mapClass(var1, var2).register();
    }

    @Override
    public <A, B> void register(Class<A> var1, Class<B> var2, String... excludes) {
        MapperFactory.getCopyByRefMapper().mapClass(var1, var2).exclude(excludes).register();
    }

}

