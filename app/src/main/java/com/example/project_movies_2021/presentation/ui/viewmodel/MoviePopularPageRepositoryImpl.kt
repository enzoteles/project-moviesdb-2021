package com.example.project_movies_2021.presentation.ui.viewmodel

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.example.project_movies_2021.data.remote.Result
import com.example.project_movies_2021.presentation.ui.paging.MoviesPopularSource
import io.reactivex.Flowable

class MoviePopularPageRepositoryImpl(
    private val datasource: MoviesPopularSource
): MoviePopularPageRepository {

    override fun getPopularMoviesPage(): Flowable<PagingData<Result>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            pagingSourceFactory = {
                datasource
            }
        ).flowable
    }
}