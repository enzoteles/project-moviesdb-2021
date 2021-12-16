package com.example.project_movies_2021.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.project_movies_2021.data.datasource.MoviesAPI
import com.example.project_movies_2021.data.remote.MoviesPopularResponse
import com.example.project_movies_2021.domain.model.ResultMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    val api: MoviesAPI
): ViewModel(){

    private val _popularMovies = MutableLiveData<List<ResultMapper>>()
    val popularMovies: LiveData<List<ResultMapper>> get() = _popularMovies

    private val _popularMoviesErrorResponse = MutableLiveData<String>()
    val popularMoviesErrorResponse: LiveData<String?> get() = _popularMoviesErrorResponse

    @SuppressLint("CheckResult")
    fun getPopularMovies(){

        api.getPopularMovies(MoviesAPI.API_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->
                _popularMovies.value = response?.results?.map { it.toResultMapper() }
            },{ error->
                _popularMoviesErrorResponse.value = error.message
            })
    }
}