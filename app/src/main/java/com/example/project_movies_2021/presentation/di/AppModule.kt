package com.example.project_movies_2021.presentation.di

import com.example.project_movies_2021.data.datasource.MoviesAPI
import com.example.project_movies_2021.data.repository.MoviesPopularRepositoryImpl
import com.example.project_movies_2021.domain.repository.MoviesPopularRepository
import com.example.project_movies_2021.presentation.MainViewModel
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}

val apiModule = module {
    single(createdAtStart = false) {
        get<Retrofit>().create(MoviesAPI::class.java)
    }
}

val moviesPopularModule = module {

    //inject interface
    single<MoviesPopularRepository>{
        MoviesPopularRepositoryImpl(
            api = get()
        )
    }

    viewModel {
        MainViewModel(
          repository = get()
        )
    }
}



