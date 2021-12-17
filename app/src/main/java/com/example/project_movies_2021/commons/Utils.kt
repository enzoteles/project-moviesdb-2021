package com.example.project_movies_2021.commons

import android.util.Log
import android.widget.ViewFlipper
import androidx.fragment.app.Fragment
import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import java.io.IOException

fun Fragment.displayedChild(value: Int, vf: ViewFlipper) {
    vf.displayedChild = value
}

data class ErrorMessage(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("message")
    val message: String = ""
)

fun convertErrorApi(error: Throwable): ErrorMessage{
    if(error is HttpException){
        try {
            return ErrorMessage(error.code(), error.message.toString())
        }catch (e: IOException){
            Log.d("error", " ${e.printStackTrace()}")
        }
    }
    return ErrorMessage()
}


