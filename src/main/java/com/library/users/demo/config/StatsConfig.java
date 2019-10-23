package com.library.users.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("stats")
public class StatsConfig {

    private String url;

    public StatsConfig() {
    }

    public StatsConfig(String url) {
        this.url = url;
    }
}
