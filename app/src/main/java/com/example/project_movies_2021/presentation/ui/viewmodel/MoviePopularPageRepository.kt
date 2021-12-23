package com.example.project_movies_2021.presentation.ui.viewmodel

import androidx.paging.PagingData
import com.example.project_movies_2021.data.remote.Result
import io.reactivex.Flowable

interface MoviePopularPageRepository {
    fun getPopularMoviesPage(): Flowable<PagingData<Result>>
}