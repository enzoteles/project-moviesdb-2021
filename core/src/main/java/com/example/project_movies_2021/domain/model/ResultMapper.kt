package com.example.project_movies_2021.domain.model

import java.io.Serializable

data class ResultMapper(
    val id: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    val original_language: String,
    val vote_average: String,
    val poster_path: String,
    val backdrop_path: String
): Serializable