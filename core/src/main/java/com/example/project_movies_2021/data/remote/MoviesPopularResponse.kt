package com.example.project_movies_2021.data.remote

import com.example.project_movies_2021.domain.model.MoviesPopularMapper

data class MoviesPopularResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
){
    fun toMoviesPopularMapper() = MoviesPopularMapper(
        page = page,
        results = results.map { it.toResultMapper() },
        total_pages = total_pages
    )
}

