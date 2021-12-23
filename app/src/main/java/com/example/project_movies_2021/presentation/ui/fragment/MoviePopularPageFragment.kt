package com.example.project_movies_2021.presentation.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_movies_2021.commons.ApiResponse
import com.example.project_movies_2021.commons.ErrorMessage
import com.example.project_movies_2021.commons.displayedChild
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

        Handler().postDelayed({
            displayedChild(2, binding!!.vfListRxResult)
            binding?.tvMovieError?.text = errorMessage.message
        }, 1000)
    }

    private fun populateMovie(data: PagingData<Result>) {
        Handler().postDelayed({
            displayedChild(1, binding!!.vfListRxResult)
            movieAdapter.submitData(lifecycle, data)
        }, 1000)

    }

    private fun showLoading() {
        displayedChild(0, binding!!.vfListRxResult)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vModel.destroy()
        binding = null
    }

    override fun onStart() {
        super.onStart()
        vModel.start()
    }
}

