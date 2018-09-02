package com.example.medicine.config;

import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@MapperScan(basePackages = {"com.example.medicine.dao"})
public class DatasourceConfig {

    @Bean(name = "dataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.hikari.primary")
    public DataSource dataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }


    @Bean(name = "dataSource2")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.second")
    public DataSource dataSource2() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }



    /*@Bean
    @Primary
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource());
        return sqlSessionFactory;
    }*/


    @Primary
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        ThreadLocalRountingDataSource localRountingDataSource=new ThreadLocalRountingDataSource();
        localRountingDataSource.setDefaultTargetDataSource(dataSource());

        Map<Object, Object> dsMap = new ConcurrentHashMap<>();
        dsMap.put(DataSources.MEDICINE,dataSource());
        dsMap.put(DataSources.MEDICINE2,dataSource2());

        localRountingDataSource.setTargetDataSources(dsMap);

        return localRountingDataSource;
    }

    /*
      注解事务
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }

}
