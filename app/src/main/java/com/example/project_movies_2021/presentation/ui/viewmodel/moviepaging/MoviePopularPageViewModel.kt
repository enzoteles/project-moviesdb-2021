package com.example.project_movies_2021.presentation.ui.viewmodel.moviepaging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.example.project_movies_2021.commons.ApiResponse
import com.example.project_movies_2021.commons.convertErrorApi
import com.example.project_movies_2021.data.remote.MoviesPopularResponse
import com.example.project_movies_2021.data.remote.Result
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class MoviePopularPageViewModel(
    private val useCase: MoviesPopularPageUseCase
) : ViewModel() {

    private val _popularMovies = MutableLiveData<ApiResponse<PagingData<Result>>>()
    val popularMovies: LiveData<ApiResponse<PagingData<Result>>> get() = _popularMovies

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
                        data = response
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

