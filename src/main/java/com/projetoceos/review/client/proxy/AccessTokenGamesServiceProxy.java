package com.projetoceos.review.client.proxy;

import com.projetoceos.review.dto.GamesAccessTokenDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "accessTokenGames", url = "${games.auth-url}")
public interface AccessTokenGamesServiceProxy {
  
    @PostMapping(value = "/token", produces = "application/json", consumes = "application/json")
    public ResponseEntity<GamesAccessTokenDTO> getAccessToken(
        @RequestParam("client_id") String clientId,
        @RequestParam("client_secret") String clientSecret,
        @RequestParam("grant_type") String grantType);
}