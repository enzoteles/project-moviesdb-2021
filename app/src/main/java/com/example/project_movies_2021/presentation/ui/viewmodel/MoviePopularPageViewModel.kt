package com.example.project_movies_2021.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.example.project_movies_2021.data.remote.Result
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MoviePopularPageViewModel(
    private val repository: MoviePopularPageRepository
) : ViewModel() {

    @ExperimentalCoroutinesApi
    fun getPopularMoviesPage(): Flowable<PagingData<Result>>{
        return repository
            .getPopularMoviesPage()
            .cachedIn(viewModelScope)
    }
}

