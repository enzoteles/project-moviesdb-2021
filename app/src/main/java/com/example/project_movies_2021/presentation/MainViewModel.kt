package com.example.project_movies_2021.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.project_movies_2021.data.datasource.MoviesAPI
import com.example.project_movies_2021.data.remote.MoviesPopularResponse
import com.example.project_movies_2021.domain.model.ResultMapper
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

    suspend fun getPopularMovies() = withContext(Dispatchers.Main){
        val call: Call<MoviesPopularResponse> = api.getPopularMovies()
        call.enqueue(
            object : Callback<MoviesPopularResponse>{
                override fun onResponse(
                    call: Call<MoviesPopularResponse>,
                    response: Response<MoviesPopularResponse>
                ) {
                    if (response.isSuccessful){
                        _popularMovies.value = response.body()?.results?.map { it.toResultMapper() }
                    }
                }

                override fun onFailure(call: Call<MoviesPopularResponse>, error: Throwable) {
                    _popularMoviesErrorResponse.value = error.message
                }

            }
        )

    }

}