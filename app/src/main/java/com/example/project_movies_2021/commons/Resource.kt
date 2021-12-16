package com.example.project_movies_2021.commons

import okhttp3.ResponseBody

sealed class Resource<out T>{
    data class Success<out T> (val value: T) : Resource<T>()
    data class Failure(
        val isNetWorkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody
    )
}