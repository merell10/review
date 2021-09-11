package com.projetoceos.review.controller;

import java.util.List;

import com.projetoceos.review.client.GamesClient;
import com.projetoceos.review.config.GamesClientProperties;
import com.projetoceos.review.dto.GameDTO;
import com.projetoceos.review.dto.GamesResultsDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class GamesController {

    @Autowired
    GamesClient gamesClient;

    @Autowired 
    private GamesClientProperties gamesClientProperties;
    
    @GetMapping("/games")
    public List<GamesResultsDTO> searchGames(@RequestParam String search){
        String query = "fields *; search \"" + search + "\"; limit 5;";
        return gamesClient.search(gamesClientProperties.getClientId(), query);
    }

    @GetMapping("/games/{id}")
    public List<GameDTO> getGame(@PathVariable String id){
        String query = "fields name, version_title, first_release_date, genres, cover, url, summary; where id = " + id + ";";
        return gamesClient.getGames(gamesClientProperties.getClientId(), query);
    }
}