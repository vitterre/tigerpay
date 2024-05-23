package com.tigerpay.auth.config;

import com.tigerpay.auth.config.properties.DatabaseProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DatabaseConfig {

    private final DatabaseProperties databaseProperties;

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    public HikariConfig hikariConfig() {
        val hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(databaseProperties.getUrl());
        hikariConfig.setMaximumPoolSize(databaseProperties.getPoolSize());
        hikariConfig.setUsername(databaseProperties.getUsername());
        hikariConfig.setPassword(databaseProperties.getPassword());
        hikariConfig.setDriverClassName(databaseProperties.getDriverClassName());
        return hikariConfig;
    }

    @Bean
    public SpringLiquibase liquibase() {
        val liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:/db/database-changelog-master.yaml");
        liquibase.setDataSource(dataSource());
        return liquibase;
    }

    @Bean
    public TransactionAwareDataSourceProxy transactionAwareDataSourceProxy() {
        return new TransactionAwareDataSourceProxy(dataSource());
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public DataSourceConnectionProvider dataSourceConnectionProvider() {
        return new DataSourceConnectionProvider(transactionAwareDataSourceProxy());
    }

    @Bean
    public ExceptionTranslator exceptionTranslator() {
        return new ExceptionTranslator();
    }

    @Bean
    public DSLContext dslContext() {
        return new DefaultDSLContext(jooqDefaultConfiguration());
    }

    @Bean
    public DefaultConfiguration jooqDefaultConfiguration() {
        val jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.set(dataSourceConnectionProvider());
        jooqConfiguration.set(new DefaultExecuteListenerProvider(exceptionTranslator()));
        jooqConfiguration.set(SQLDialect.POSTGRES);
        return jooqConfiguration;
    }
}
