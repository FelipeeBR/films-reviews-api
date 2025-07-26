package com.filmsreviews.filmsreviews_api.controller.dto;

import java.util.List;

public record FilmDto(List<FilmItemDto> films, int page, int size, int totalPages, long totalElements) {

}
