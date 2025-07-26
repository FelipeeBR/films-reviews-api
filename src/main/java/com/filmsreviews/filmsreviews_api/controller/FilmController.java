package com.filmsreviews.filmsreviews_api.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.filmsreviews.filmsreviews_api.controller.dto.CreateFilmDto;
import com.filmsreviews.filmsreviews_api.entities.Film;
import com.filmsreviews.filmsreviews_api.repository.FilmRepository;
import com.filmsreviews.filmsreviews_api.repository.UserRepository;

@RestController
public class FilmController {

    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    public FilmController(FilmRepository filmRepository, UserRepository userRepository) {
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/films")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> createFilm(@RequestBody CreateFilmDto dto, JwtAuthenticationToken token) {
        var user = userRepository.findById(UUID.fromString(token.getName()));

        var film = new Film();
        film.setUser(user.get());
        film.setTitle(dto.title());
        film.setGenre(dto.genre());
        film.setDescription(dto.description());
        film.setImage(dto.image());
        filmRepository.save(film);
        return ResponseEntity.ok().build();
    }
}
