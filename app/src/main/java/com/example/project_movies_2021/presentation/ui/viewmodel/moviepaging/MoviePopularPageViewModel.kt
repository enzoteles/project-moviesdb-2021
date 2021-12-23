package com.example.project_movies_2021.presentation.ui.viewmodel.moviepaging

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.example.project_movies_2021.commons.ApiResponse
import com.example.project_movies_2021.commons.convertErrorApi
import com.example.project_movies_2021.domain.model.ResultMapper
import com.example.project_movies_2021.presentation.component.adapter.SinglePagingLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class MoviePopularPageViewModel(
    private val useCase: MoviesPopularPageUseCase
) : ViewModel() {

    private val _popularMovies = SinglePagingLiveEvent<ApiResponse<PagingData<ResultMapper>>>()
    val popularMovies: LiveData<ApiResponse<PagingData<ResultMapper>>> get() = _popularMovies

    var disposable = CompositeDisposable()

    @ExperimentalCoroutinesApi
    fun getPopularMoviesPage() = viewModelScope.launch {
             useCase
                .invoke()
                .observeOn(AndroidSchedulers.mainThread())
                 .doOnSubscribe {
                     _popularMovies.value = ApiResponse.Loading
                 }
                .subscribe({ response ->
                    _popularMovies.value = ApiResponse.Success(
                        data = response.map { it.toResultMapper() }
                    )
                },{ err ->
                    _popularMovies.value = ApiResponse.Failure(
                         errorMessage = convertErrorApi(err)
                    )
                }).addTo(disposable)
        }

    fun start() {
        if (disposable.isDisposed) {
            disposable = CompositeDisposable()
        }
    }

    fun destroy() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

}





