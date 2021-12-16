package com.example.project_movies_2021.data.remote

import com.example.project_movies_2021.domain.model.ResultMapper

data class Result(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
){
    fun toResultMapper() = ResultMapper(
        id = id,
        title = title,
        overview = overview
    )
}