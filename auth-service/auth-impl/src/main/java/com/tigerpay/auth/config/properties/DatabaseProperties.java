package com.tigerpay.auth.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class DatabaseProperties {

    private final String url;
    private final String username;
    private final String password;
    private final String driverClassName;
    private final Integer poolSize;

    public DatabaseProperties(final @Value("${db.url}")                  String url,
                              final @Value("${db.hikari.max-pool-size}") Integer poolSize,
                              final @Value("${db.username}")             String username,
                              final @Value("${db.password}")             String password,
                              final @Value("${db.driver.classname}")     String driverClassName) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
        this.poolSize = poolSize;
    }
}
