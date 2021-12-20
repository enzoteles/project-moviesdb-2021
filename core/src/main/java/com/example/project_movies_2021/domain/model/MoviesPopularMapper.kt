package com.example.project_movies_2021.domain.model

import java.io.Serializable

data class MoviesPopularMapper(
    val page: Int,
    val results: List<ResultMapper>,
    val total_pages: Int,
): Serializable