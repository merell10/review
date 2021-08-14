package com.projetoceos.review.controller;

import java.util.List;

import com.projetoceos.review.entity.Review;
import com.projetoceos.review.repository.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping
    public Iterable<Review> findAll(){
        return reviewRepository.findAll();
    }

    @GetMapping("/title/{reviewTitle}")
    public List<Review> findByTitle(@PathVariable String reviewTitle){
        return reviewRepository.findByTitle(reviewTitle);
    }

    @GetMapping("/{id}")
    public Review findOne(@PathVariable Long id){
        //TODO Implement error handler
        return reviewRepository.findById(id).orElseThrow(null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Review create(@RequestBody Review review){
        return reviewRepository.save(review);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        //TODO Implement error handler
        reviewRepository.findById(id).orElseThrow(null);

        reviewRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Review updateReview(@RequestBody Review review, @PathVariable Long id){
        //TODO Implement error handler
        Review reviewToUpdate = reviewRepository.findById(id).orElseThrow(null);
        if(id.equals(reviewToUpdate.getId())){
            review.setId(id);
        }else{
            //TODO Implement error handler
        }
        
        return reviewRepository.save(review);
    }


}
