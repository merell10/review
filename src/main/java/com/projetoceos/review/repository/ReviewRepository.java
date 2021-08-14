package com.projetoceos.review.repository;

import java.util.List;

import com.projetoceos.review.entity.Review;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReviewRepository extends PagingAndSortingRepository<Review, Long>, JpaSpecificationExecutor<Review> {
    List<Review> findByTitle(String title);
}
