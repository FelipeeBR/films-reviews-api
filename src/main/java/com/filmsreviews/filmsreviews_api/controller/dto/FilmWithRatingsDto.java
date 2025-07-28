package com.filmsreviews.filmsreviews_api.controller.dto;

import java.util.List;

public record FilmWithRatingsDto(Long id, String title, String description, List<RatingDto> ratings) {

}
