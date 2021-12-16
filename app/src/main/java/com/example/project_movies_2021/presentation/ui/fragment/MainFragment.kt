package com.example.project_movies_2021.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project_movies_2021.commons.ApiResponse
import com.example.project_movies_2021.databinding.FragmentMainBinding
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

        binding.btnOk.setOnClickListener {
            callPopularMovies()
        }

        model.popularMovies.observe(this,{ response ->
            when(response){
                is ApiResponse.Loading->{
                    Log.i("response", "loading")
                }
                is ApiResponse.Success->{
                    Log.i("response", response.data[0].title)
                }
                is ApiResponse.Failure->{
                    Log.i("response", response.msg)
                }
            }
        })
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
    }

}