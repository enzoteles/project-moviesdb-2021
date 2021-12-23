package com.example.project_movies_2021.presentation.di

import com.example.project_movies_2021.data.datasource.MoviesAPI
import com.example.project_movies_2021.data.repository.MoviesPopularRepositoryImpl
import com.example.project_movies_2021.domain.repository.MoviesPopularRepository
import com.example.project_movies_2021.domain.usecase.MoviesPopularUseCase
import com.example.project_movies_2021.domain.usecase.MoviesPopularUseCaseImpl
import com.example.project_movies_2021.presentation.ui.paging.MoviesPopularSource
import com.example.project_movies_2021.presentation.ui.viewmodel.*
import com.example.project_movies_2021.presentation.ui.viewmodel.moviepaging.*
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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

    //inject repository
    single<MoviesPopularRepository>{
        MoviesPopularRepositoryImpl(
            api = get()
        )
    }

    // datasource page
    factory {
        MoviesPopularSource(api = get())
    }

    //inject repository page
    single<MoviePopularPageRepository>{
        MoviePopularPageRepositoryImpl(
            datasource = get()
        )
    }

    //inject useCase
    single<MoviesPopularUseCase>{
        MoviesPopularUseCaseImpl(
            repository = get()
        )
    }

    single<MoviesPopularPageUseCase>{
        MoviesPopularPageUseCaseImpl(
            repository = get()
        )
    }

    //viewModel
    viewModel {
        MainViewModel(
          moviesPopularUseCase = get(),
            AndroidSchedulers.mainThread(),
            Schedulers.io()
        )
    }

    viewModel {
        DefaultViewModel()
    }

    viewModel {
        MoviePopularPageViewModel(
            useCase = get()
        )
    }


}



