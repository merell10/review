package com.projetoceos.review.service;

import java.util.ArrayList;
import java.util.List;

import com.projetoceos.review.entity.Review;
import com.projetoceos.review.exception.BadResourceException;
import com.projetoceos.review.exception.ResourceAlreadyExistsException;
import com.projetoceos.review.exception.ResourceNotFoundException;
import com.projetoceos.review.repository.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    private boolean existsById(Long id) {
        return reviewRepository.existsById(id);
    }

    public Review findById(Long id) throws ResourceNotFoundException {
        Review review = reviewRepository.findById(id).orElse(null);
        if (review == null) {
            throw new ResourceNotFoundException("Cannot find Review with id: " + id);
        } else
            return review;
    }

    public List<Review> findAll(int pageNumber, int rowPerPage) {
        List<Review> reviews = new ArrayList<>();
        Pageable sortedByIdAsc = PageRequest.of(pageNumber - 1, rowPerPage, Sort.by("id").ascending());

        reviewRepository.findAll(sortedByIdAsc).forEach(reviews::add);

        return reviews;
    }

    public Review save(Review review) throws BadResourceException, ResourceAlreadyExistsException {
        if (review.getRating() >= 0) {
            if (existsById(review.getId())) {
                throw new ResourceAlreadyExistsException("Review with id: " + review.getId() + " already exists");
            }
            return reviewRepository.save(review);
        } else {
            BadResourceException exc = new BadResourceException("Failed to save review");
            exc.addErrorMessage("Review is null or empty");
            throw exc;
        }
    }

    public void update(Review review) throws BadResourceException, ResourceNotFoundException {
        if (review.getRating() >= 0) {
            if (!existsById(review.getId())) {
                throw new ResourceNotFoundException("Cannot find Review with id: " + review.getId());
            }
            reviewRepository.save(review);
        } else {
            BadResourceException exc = new BadResourceException("Failed to save review");
            exc.addErrorMessage("Review is null or empty");
            throw exc;
        }
    }

    public void deleteById(Long id) throws ResourceNotFoundException {
        if (!existsById(id)) {
            throw new ResourceNotFoundException("Cannot find review with id: " + id);
        } else {
            reviewRepository.deleteById(id);
        }
    }

    public Long count() {
        return reviewRepository.count();
    }
}
