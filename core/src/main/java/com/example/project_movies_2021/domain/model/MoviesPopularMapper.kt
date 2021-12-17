package com.example.project_movies_2021.domain.model

data class MoviesPopularMapper(
    val page: Int,
    val results: List<ResultMapper>,
    val total_pages: Int,
)