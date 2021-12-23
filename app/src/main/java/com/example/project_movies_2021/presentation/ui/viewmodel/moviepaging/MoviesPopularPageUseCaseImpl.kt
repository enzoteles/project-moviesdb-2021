package com.example.project_movies_2021.presentation.ui.viewmodel.moviepaging

import androidx.paging.PagingData
import com.example.project_movies_2021.data.remote.Result
import io.reactivex.Flowable

class MoviesPopularPageUseCaseImpl(
    private val repository: MoviePopularPageRepository
): MoviesPopularPageUseCase
{
    override fun invoke(): Flowable<PagingData<Result>>  = repository.getPopularMoviesPage()
}
