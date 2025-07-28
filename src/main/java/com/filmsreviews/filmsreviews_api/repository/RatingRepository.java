package com.filmsreviews.filmsreviews_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.filmsreviews.filmsreviews_api.entities.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {

}
