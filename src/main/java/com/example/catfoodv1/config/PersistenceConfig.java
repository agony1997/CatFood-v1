package com.example.catfoodv1.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

/**
 * Provides explicit, manual configuration for the persistence layer (JPA).
 *
 * This class takes control of the EntityManagerFactory creation from Spring Boot's
 * auto-configuration. By defining the LocalContainerEntityManagerFactoryBean bean
 * ourselves and annotating it with @DependsOn("flyway"), we create an
 * unbreakable, explicit dependency.
 *
 * This guarantees that the EntityManagerFactory will only be created *after* the
 * 'flyway' bean has been fully initialized and its database migrations have
 * completed, thus permanently resolving the startup race condition.
 */
@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.catfoodv1.repo",
        entityManagerFactoryRef = "entityManagerFactory"
)
public class PersistenceConfig {

    @Bean
    @DependsOn("flyway") // This is the crucial instruction.
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.example.catfoodv1.model.entity") // Scan for @Entity classes
                .build();
    }
}