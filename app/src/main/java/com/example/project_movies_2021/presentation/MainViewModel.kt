package com.example.project_movies_2021.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project_movies_2021.commons.ApiResponse
import com.example.project_movies_2021.commons.convertErrorApi
import com.example.project_movies_2021.data.datasource.MoviesAPI
import com.example.project_movies_2021.domain.model.ResultMapper
import com.example.project_movies_2021.domain.repository.MoviesPopularRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MainViewModel(
    private val repository: MoviesPopularRepository
) : ViewModel() {

    private val _popularMovies = MutableLiveData<ApiResponse<List<ResultMapper>>>()
    val popularMovies: LiveData<ApiResponse<List<ResultMapper>>> get() = _popularMovies


    var disposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    fun getPopularMovies() {

        ApiResponse.Loading
        repository.getPopularMovies(MoviesAPI.API_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                _popularMovies.value = ApiResponse.Loading
            }
            .subscribe({ response ->
                _popularMovies.value = ApiResponse.Success(
                    data = response.results.map { it.toResultMapper() }
                )
            }, { error ->
                _popularMovies.value = ApiResponse.Failure(
                    errorMessage = convertErrorApi(error)
                )
            })
    }

    fun start() {
        if (disposable.isDisposed) {
            disposable = CompositeDisposable()
        }
    }

    fun destroy() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }
}