package com.spring.social.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Configuration
@ConfigurationProperties(prefix = "oauth2")
public class OAuth2Properties {

    List<String> authorizedRedirectUris = new ArrayList<>();
}
