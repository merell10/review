package com.projetoceos.review.client;

import java.util.List;

import com.projetoceos.review.config.GamesConfig;
import com.projetoceos.review.dto.GameDTO;
import com.projetoceos.review.dto.GamesResultsDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "gamesClient", url = "${games.base-url}", configuration = GamesConfig.class)
public interface GamesClient {

    @PostMapping(value = "/search", produces = "application/json", consumes = "application/json")
    List<GamesResultsDTO> search(
        @RequestHeader("Client-ID") String clientId,
        @RequestBody String query);

    @PostMapping(value = "/games", produces = "application/json", consumes = "application/json")
    List<GameDTO> getGames(
        @RequestHeader("Client-ID") String clientId,
        @RequestBody String query);

}