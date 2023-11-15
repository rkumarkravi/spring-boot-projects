package com.rkumarkravi.instaclone.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.rkumarkravi.instaclone.dao",
        entityManagerFactoryRef = "oracleEntityManagerFactory",
        transactionManagerRef = "oracleTransactionManager"
)
@EntityScan(basePackages = "com.rkumarkravi.instaclone.entity")
public class OracleConfig {

    @Primary
    @Bean(name = "oracleDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.oracle")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "oracleEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("oracleDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.rkumarkravi.instaclone.entity")
                .persistenceUnit("oracle")
                .properties(jpaProperties())
                .build();
    }

    @Primary
    @Bean(name = "oracleTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("oracleEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    private Map<String, Object> jpaProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");  // or "create", "create-drop", "validate", "none"
        properties.put("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
        // Add any other JPA properties as needed
        return properties;
    }
}

