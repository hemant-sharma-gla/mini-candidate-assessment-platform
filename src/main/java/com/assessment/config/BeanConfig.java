package com.assessment.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

/**
 * Configuration class for creating and configuring Spring beans.
 * This class provides bean definitions for various components used in the application.
 */
@Configuration
public class BeanConfig {

    /**
     * Creates and configures an ObjectMapper bean with custom settings.
     * The configured ObjectMapper will ignore unknown properties during deserialization
     * to prevent errors when processing JSON with additional fields not present in the target class.
     *
     * @return A configured ObjectMapper instance with FAIL_ON_UNKNOWN_PROPERTIES set to false
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

}
