package com.example.project_movies_2021.domain.usecase

import com.example.project_movies_2021.data.remote.MoviesPopularResponse
import com.example.project_movies_2021.domain.repository.MoviesPopularRepository
import io.reactivex.Observable

interface MoviesPopularUseCase
{
    operator fun invoke(key: String) : Observable<MoviesPopularResponse>
}
