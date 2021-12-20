package com.example.project_movies_2021.data.remote

import com.example.project_movies_2021.domain.model.ResultMapper
import com.example.project_movies_2021.utils.DateTimeHelper
import com.example.project_movies_2021.utils.toPtBrRealString

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
        original_language = original_language,
        title = title,
        overview = overview,
        release_date = DateTimeHelper.convertToDate(release_date, "yyyy-MM-dd", "dd/MM/yyyy"),
        vote_average = "R$ "+ vote_average.toPtBrRealString()

    )
}