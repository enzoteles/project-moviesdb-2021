package com.example.project_movies_2021.domain.repository

import com.example.project_movies_2021.data.datasource.MoviesAPI
import com.example.project_movies_2021.data.remote.MoviesPopularResponse
import io.reactivex.Observable

interface MoviesPopularRepository {

    fun getPopularMovies(
        apiKey: String = MoviesAPI.API_KEY
    ) : Observable<MoviesPopularResponse>
}