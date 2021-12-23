package com.example.project_movies_2021.presentation.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_movies_2021.R
import com.example.project_movies_2021.databinding.MoviePopularPageFragmentBinding
import com.example.project_movies_2021.presentation.ui.adapter.MoviePopularAdapter
import com.example.project_movies_2021.presentation.ui.base.BaseFragment
import com.example.project_movies_2021.presentation.ui.viewmodel.MoviePopularPageViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
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
        mDisposable.add(
            vModel.getPopularMoviesPage().subscribe{
                movieAdapter.submitData(lifecycle, it)
            }
        )

    }
}

