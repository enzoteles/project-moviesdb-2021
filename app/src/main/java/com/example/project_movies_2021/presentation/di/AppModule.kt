package com.example.project_movies_2021.presentation.di

import com.example.project_movies_2021.data.datasource.MoviesAPI
import com.example.project_movies_2021.presentation.MainViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val retrofitModule = module {
    factory<Interceptor> {
        HttpLoggingInterceptor(
            HttpLoggingInterceptor.Logger.DEFAULT
        ).setLevel(
            HttpLoggingInterceptor.Level.BODY
        )
    }

    factory {
        OkHttpClient.Builder().addInterceptor(
            interceptor = get()
        ).build()
    }
    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(MoviesAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
}

val apiModule = module {
    single(createdAtStart = false) {
        get<Retrofit>().create(MoviesAPI::class.java)
    }
}

val viewModuleModule = module {
    viewModel {
        MainViewModel(
          api = get()
        )
    }
}