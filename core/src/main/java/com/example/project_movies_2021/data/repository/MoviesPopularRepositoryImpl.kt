package com.example.project_movies_2021.data.repository

import com.example.project_movies_2021.data.datasource.MoviesAPI
import com.example.project_movies_2021.data.remote.MoviesPopularResponse
import com.example.project_movies_2021.domain.repository.MoviesPopularRepository
import io.reactivex.Single

class MoviesPopularRepositoryImpl(
    private val api: MoviesAPI
) : MoviesPopularRepository {
    override fun getPopularMovies(apiKey: String) = api.getPopularMovies(apiKey)
}