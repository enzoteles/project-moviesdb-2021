package com.example.project_movies_2021

import android.app.Application
import com.example.project_movies_2021.presentation.di.apiModule
import com.example.project_movies_2021.presentation.di.moviesPopularModule
import com.example.project_movies_2021.presentation.di.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@App)
            modules(
                retrofitModule,
                apiModule,
                moviesPopularModule
            )
        }
    }
}