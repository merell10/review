package com.projetoceos.review.controller;

import java.util.List;

import com.projetoceos.review.config.ApplicationProperties;
import com.projetoceos.review.entity.Review;
import com.projetoceos.review.exception.ResourceNotFoundException;
import com.projetoceos.review.service.ReviewService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SimpleController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 5;

    @Autowired
    ReviewService reviewService;

    @Autowired
    ApplicationProperties applicationProperties;

    @Value("${app.menu}")
    String menu;

    @GetMapping(value = { "/", "/home" })
    public String homePage(Model model) {
        model.addAttribute("title", applicationProperties.getTitle());
        model.addAttribute("menu", menu);
        return "home";
    }

    @GetMapping(value = "/reviews")
    public String getReviews(Model model, @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
        List<Review> reviews = reviewService.findAll(pageNumber, ROW_PER_PAGE);

        long count = reviewService.count();
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;
        model.addAttribute("reviews", reviews);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("prev", pageNumber - 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("next", pageNumber + 1);

        return "review-list";
    }

    @GetMapping(value = { "/reviews/add" })
    public String showAddReview(Model model) {
        Review review = new Review();
        model.addAttribute("add", true);
        model.addAttribute("review", review);

        return "review-edit";
    }

    @PostMapping(value = "/reviews/add")
    public String addReview(Model model, @ModelAttribute("review") Review review) {
        try {
            Review newReview = reviewService.save(review);
            return "redirect:/reviews/" + String.valueOf(newReview.getId());
        } catch (Exception ex) {
            // log exception first,
            // then show error
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            // model.addAttribute("review", review);
            model.addAttribute("add", true);
            return "review-edit";
        }
    }

    @GetMapping(value = { "/reviews/{reviewId}/edit" })
    public String showEditReview(Model model, @PathVariable long reviewId) {
        Review review = null;
        try {
            review = reviewService.findById(reviewId);
        } catch (ResourceNotFoundException ex) {
            model.addAttribute("errorMessage", "Review not found");
        }
        model.addAttribute("add", false);
        model.addAttribute("review", review);
        return "review-edit";
    }

    @PostMapping(value = { "/reviews/{reviewId}/edit" })
    public String updateReview(Model model, @PathVariable long reviewId, @ModelAttribute("review") Review review) {
        try {
            review.setId(reviewId);
            reviewService.update(review);
            return "redirect:/reviews/" + String.valueOf(review.getId());
        } catch (Exception ex) {
            // log exception first,
            // then show error
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("add", false);
            return "review-edit";
        }
    }

    @GetMapping(value = "/reviews/{reviewId}")
    public String getReviewById(Model model, @PathVariable long reviewId) {
        Review review = null;
        try {
            review = reviewService.findById(reviewId);
        } catch (ResourceNotFoundException ex) {
            model.addAttribute("errorMessage", "Review not found");
        }
        model.addAttribute("review", review);
        return "review";
    }

    @GetMapping(value = { "/reviews/{reviewId}/delete" })
    public String showDeleteReviewById(Model model, @PathVariable long reviewId) {
        Review review = null;
        try {
            review = reviewService.findById(reviewId);
        } catch (ResourceNotFoundException ex) {
            model.addAttribute("errorMessage", "Review not found");
        }
        model.addAttribute("allowDelete", true);
        model.addAttribute("review", review);
        return "review";
    }

    @PostMapping(value = { "/reviews/{reviewId}/delete" })
    public String deleteReviewById(Model model, @PathVariable long reviewId) {
        try {
            reviewService.deleteById(reviewId);
            return "redirect:/reviews";
        } catch (ResourceNotFoundException ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "review";
        }
    }
}
