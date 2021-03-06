package com.example.project_movies_2021.commons

sealed class ApiResponse<out T>{

    data class Success<out R>(val data: R) : ApiResponse<R>()
    data class Failure(
        val errorMessage: ErrorMessage
        ) : ApiResponse<Nothing>()

    object Loading : ApiResponse<Nothing>()
}
