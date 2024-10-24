package com.brokerage.config;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class ActuatorConfig {

    @Bean
    public InfoContributor customInfoContributor() {
        return (Info.Builder builder) -> builder.withDetail("project",
                Collections.singletonMap("description", "Brokerage Firm API with custom health checks and info")).build();
    }
}
