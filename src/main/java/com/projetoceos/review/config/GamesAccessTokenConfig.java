package com.projetoceos.review.config;

import java.util.Date;

import com.projetoceos.review.client.proxy.AccessTokenGamesServiceProxy;
import com.projetoceos.review.dto.GamesAccessTokenDTO;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import feign.RequestTemplate;

@Component
public class GamesAccessTokenConfig {
    
    private static final Logger LOGGER=LoggerFactory.getLogger(GamesAccessTokenConfig.class);
    private static final Integer DEFAULT_EXPIRES_IN = 3600;
    private static final Integer PREVENT_EXPIRATION_SECONDS = 5;
    private static final String BEARER = "Bearer %s";
    private static final String HEADER_AUTHORIZATION = "Authorization";

    private GamesAccessTokenDTO gamesAccessToken;

    @Autowired 
    private GamesClientProperties gamesClientProperties;
    
    @Autowired
    private AccessTokenGamesServiceProxy accessTokenGamesServiceProxy;


    public synchronized void verify(RequestTemplate requestTemplate){
        
        if(gamesAccessToken == null || gamesAccessToken.isExpired()){
            this.gamesAccessToken = getAccessToken();
            LOGGER.warn("getAccessToken() {}", getAccessToken());

        }

        if(this.gamesAccessToken != null){
            String accessTokenHeader = String.format(BEARER, this.gamesAccessToken.getAccessToken());
            requestTemplate.header(HEADER_AUTHORIZATION, accessTokenHeader);
        }

    }

    private GamesAccessTokenDTO getAccessToken(){
        try{
            GamesAccessTokenDTO token = null;
            
            LOGGER.info("gamesClientProperties.getClientId() {}", gamesClientProperties.getClientId());
            LOGGER.info("gamesClientProperties.getSecret() {}", gamesClientProperties.getSecret());
            LOGGER.info("gamesClientProperties.getSecret() {}", gamesClientProperties.getGrantType());

            ResponseEntity<GamesAccessTokenDTO> response = accessTokenGamesServiceProxy.getAccessToken(
                gamesClientProperties.getClientId(), gamesClientProperties.getSecret(), gamesClientProperties.getGrantType());

            LOGGER.info("RESPONSE {}", response);

            if(response != null){
                token = response.getBody();

                Date expiresAt = DateUtils.addSeconds(new Date(), DEFAULT_EXPIRES_IN - PREVENT_EXPIRATION_SECONDS);
                if(token != null){
                    if(token.getExpiresIn() != null){
                        token.setExpiresAt(DateUtils.addSeconds(new Date(), token.getExpiresIn() - PREVENT_EXPIRATION_SECONDS));
                    }else{
                        token.setExpiresAt(expiresAt);
                    }
                }
            }

            return token;

        }catch (Exception e){
            //TODO implement error handler
            return null;
        }
    }
}