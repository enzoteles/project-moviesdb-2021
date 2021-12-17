package com.example.project_movies_2021.commons

import com.google.gson.annotations.SerializedName

data class ErrorMessage(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("message")
    val message: String = ""
)