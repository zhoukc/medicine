package com.example.medicine.expands;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

//@JsonComponent
public class JsonSerializerManage {

    // @Bean
    //@ConditionalOnMissingBean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        //设置序列化规则，不能允许为空，当value为null时，key不进行序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        //objectMapper.setSerializerFactory()  根据需求可以自定义序列化工厂和提供者
        //objectMapper.setSerializerProvider()
        return objectMapper;
    }
}