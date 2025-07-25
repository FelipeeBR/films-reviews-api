package com.filmsreviews.filmsreviews_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filmsreviews.filmsreviews_api.entities.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

}
