package com.projetoceos.review.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GamesAccessTokenDTO {
    
    @JsonProperty("access_token")
    private String accessToken;
    
    @JsonProperty("expires_in")
    private Integer expiresIn;

    @JsonProperty("token_type")
    private String tokenType;

    private Date expiresAt;

    public boolean isExpired(){
        return (expiresAt != null) && (expiresAt.before(new Date()));
    }
}