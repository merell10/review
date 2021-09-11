package com.projetoceos.review.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import feign.Request;
import feign.RequestInterceptor;

public class GamesConfig {
    
    @Value("${games.connect-timeout}")
    private long connectTimeout;

    @Value("${games.read-timeout}")
    private long readTimeout;

    @Autowired
    private GamesAccessTokenConfig gamesAccessTokenConfig;

    @Bean
    public Request.Options options(){
        return new Request.Options(connectTimeout, TimeUnit.MILLISECONDS, readTimeout, TimeUnit.MILLISECONDS, true);
    }

    @Bean
    public RequestInterceptor requestInterceptor(){
        return requestTemplate -> gamesAccessTokenConfig.verify(requestTemplate);
    }

}
