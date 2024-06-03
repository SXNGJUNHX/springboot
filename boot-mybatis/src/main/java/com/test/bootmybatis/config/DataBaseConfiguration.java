package com.test.bootmybatis.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

//@Configuration
public class DataBaseConfiguration {

    @Bean
    public DataSource getDataSource() {

        return DataSourceBuilder.create()
                .driverClassName("oracle.jdbc.OracleDriver")
                .url("jdbc:oracle:thin:@localhost:1521/xe")
                .username("springboot")
                .password("java1234")
                .build();
    }

}













