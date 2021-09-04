package com.projetoceos.review.controller;

import java.util.List;


import com.projetoceos.review.dto.GameDTO;
import com.projetoceos.review.dto.GamesResultsDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("api")
public class GamesController {
    
    @GetMapping("/games")
    public List<GamesResultsDTO> searchGames (@RequestParam String search) {
        return null;
    }

    @GetMapping("/{id}")
    public List<GameDTO> Getgame(@PathVariable String Id) {
        return null;
    }

}