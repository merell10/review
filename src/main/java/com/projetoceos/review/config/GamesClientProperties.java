package com.projetoceos.review.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "games")
public class GamesClientProperties {

    private String clientId;

    private String secret;

    private String grantType;
    
}