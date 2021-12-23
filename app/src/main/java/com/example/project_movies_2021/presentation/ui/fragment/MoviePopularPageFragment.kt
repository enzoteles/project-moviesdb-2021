package com.example.project_movies_2021.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_movies_2021.commons.ApiResponse
import com.example.project_movies_2021.commons.ErrorMessage
import com.example.project_movies_2021.databinding.MoviePopularPageFragmentBinding
import com.example.project_movies_2021.presentation.ui.adapter.MoviePopularAdapter
import com.example.project_movies_2021.presentation.ui.base.BaseFragment
import com.example.project_movies_2021.presentation.ui.viewmodel.moviepaging.MoviePopularPageViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.project_movies_2021.data.remote.Result
@ExperimentalCoroutinesApi
class MoviePopularPageFragment : BaseFragment<MoviePopularPageViewModel, MoviePopularPageFragmentBinding>() {

    lateinit var movieAdapter: MoviePopularAdapter
    private val mDisposable = CompositeDisposable()

    override fun getViewModel(): MoviePopularPageViewModel {
        val moviePopularVM : MoviePopularPageViewModel by viewModel()
        return moviePopularVM
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = MoviePopularPageFragmentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRyclerView()
        initViewModel()
    }

    private fun initRyclerView() {
        binding!!.rvMoviePopular.apply {
            layoutManager = LinearLayoutManager(requireContext())
            movieAdapter = MoviePopularAdapter()
            adapter = movieAdapter
        }
    }


    private fun initViewModel(){

        vModel.getPopularMoviesPage()
        vModel.popularMovies.observe(this,{ response->
            when(response){
                is ApiResponse.Loading->{
                    showLoading()
                }
                is ApiResponse.Success->{
                    populateMovie(response.data)
                }
                is ApiResponse.Failure->{
                    showError(response.errorMessage)
                }
            }
        })

    }

    private fun showError(errorMessage: ErrorMessage) {
        Log.i("PAGE", errorMessage.message)
    }

    private fun populateMovie(data: PagingData<Result>) {

        Log.i("PAGE", "$data")

        movieAdapter.submitData(lifecycle, data)
    }

    private fun showLoading() {
        Log.i("PAGE", "loading.....")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

