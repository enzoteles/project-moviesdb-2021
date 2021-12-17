package com.example.project_movies_2021.domain.usecase

import com.example.project_movies_2021.domain.repository.MoviesPopularRepository

class MoviesPopularUseCaseImpl(
    private val repository: MoviesPopularRepository
): MoviesPopularUseCase {
    override fun invoke(key: String) = repository.getPopularMovies(key)
}
