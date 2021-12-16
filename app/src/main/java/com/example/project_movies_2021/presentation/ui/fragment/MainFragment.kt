package com.example.project_movies_2021.presentation.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project_movies_2021.commons.ApiResponse
import com.example.project_movies_2021.commons.displayedChild
import com.example.project_movies_2021.databinding.FragmentMainBinding
import com.example.project_movies_2021.domain.model.ResultMapper
import com.example.project_movies_2021.presentation.MainViewModel
import com.example.project_movies_2021.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>(){

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMainBinding.inflate(inflater, container, false)

    override fun getViewModel(): MainViewModel {
        val mainViewModel: MainViewModel by viewModel()
             return mainViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callPopularMovies()

        model.popularMovies.observe(this,{ response ->
            when(response){
                is ApiResponse.Loading->{
                        displayedChild(0, binding!!.vfMain)
                }
                is ApiResponse.Success->{
                    Handler().postDelayed({
                        displayedChild(1, binding!!.vfMain)
                        populateMovie(response.data)
                    }, 1000)
                }
                is ApiResponse.Failure->{
                    displayedChild(2, binding!!.vfMain)
                }
            }
        })
    }

    private fun populateMovie(data: List<ResultMapper>) {
        binding?.tvName?.text = data[1].title
    }


    private fun callPopularMovies(){
        model.getPopularMovies()
    }

    override fun onResume() {
        super.onResume()
        model.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        model.destroy()
        binding = null
    }

}