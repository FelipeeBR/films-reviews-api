package com.filmsreviews.filmsreviews_api.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.filmsreviews.filmsreviews_api.controller.dto.CreateRatingDto;
import com.filmsreviews.filmsreviews_api.entities.Rating;
import com.filmsreviews.filmsreviews_api.repository.FilmRepository;
import com.filmsreviews.filmsreviews_api.repository.RatingRepository;
import com.filmsreviews.filmsreviews_api.repository.UserRepository;

@RestController
public class RatingController {

    private final RatingRepository ratingRepository;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    public RatingController(RatingRepository ratingRepository, FilmRepository filmRepository, UserRepository userRepository) {
        this.ratingRepository = ratingRepository;
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/films/{id}/ratings")
    public ResponseEntity<Void> createRating(@PathVariable("id") Long id, @RequestBody CreateRatingDto dto, JwtAuthenticationToken token) {
        var user = userRepository.findById(UUID.fromString(token.getName()));

        var film = filmRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var rating = new Rating();
        rating.setUser(user.get());
        rating.setFilm(film);
        rating.setDescription(dto.description());
        rating.setStars(dto.stars());
        ratingRepository.save(rating);
        return ResponseEntity.ok().build();
    }
}
