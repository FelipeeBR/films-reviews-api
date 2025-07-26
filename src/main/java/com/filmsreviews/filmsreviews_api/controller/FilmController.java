package com.filmsreviews.filmsreviews_api.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.filmsreviews.filmsreviews_api.controller.dto.CreateFilmDto;
import com.filmsreviews.filmsreviews_api.entities.Film;
import com.filmsreviews.filmsreviews_api.entities.Role;
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

    @DeleteMapping("/films/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> deleteFilm(@PathVariable("id") Long id, JwtAuthenticationToken token) {
        var user = userRepository.findById(UUID.fromString(token.getName()));

        var film = filmRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        var isAdmin = user.get().getRoles()
            .stream()
            .anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));
        
        if(isAdmin || film.getUser().getId().equals(UUID.fromString(token.getName()))) {
            filmRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        
        return ResponseEntity.ok().build();
    }
}
