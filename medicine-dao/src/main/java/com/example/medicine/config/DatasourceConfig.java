package com.example.medicine.config;

import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Configuration
@MapperScan(basePackages = "com.example.medicine.dao")
public class DatasourceConfig {

    @Bean(name = "dataSource", destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.primary")
    public DataSource dataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }


    @Bean(name = "dataSource2")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.second")
    public DataSource dataSource2() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }


    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        ThreadLocalRoutingDataSource localRoutingDataSource = new ThreadLocalRoutingDataSource();
        localRoutingDataSource.setDefaultTargetDataSource(dataSource());

        Map<Object, Object> dsMap = new ConcurrentHashMap<>();
        dsMap.put(DataSources.MEDICINE, dataSource());
        dsMap.put(DataSources.MEDICINE2, dataSource2());

        localRoutingDataSource.setTargetDataSources(dsMap);

        return localRoutingDataSource;
    }

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dynamicDataSource());
        return sqlSessionFactory;
    }

    /*
      注解事务
     *//*
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }*/

}
