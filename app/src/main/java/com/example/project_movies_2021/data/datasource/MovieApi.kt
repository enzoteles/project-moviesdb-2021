package com.example.project_movies_2021.data.datasource

import com.example.project_movies_2021.data.remote.MoviesPopularResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesAPI {

    @GET("/3/movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY
    ) : Observable<MoviesPopularResponse>

    companion object {
        val BASE_URL = "https://api.themoviedb.org/"
        val API_KEY = "398e535cd4549b5f8c05207c0ebd8106"
    }
}