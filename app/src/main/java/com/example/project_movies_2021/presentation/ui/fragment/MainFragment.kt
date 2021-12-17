package com.example.project_movies_2021.presentation.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project_movies_2021.commons.ApiResponse
import com.example.project_movies_2021.commons.ErrorMessage
import com.example.project_movies_2021.commons.displayedChild
import com.example.project_movies_2021.databinding.FragmentMainBinding
import com.example.project_movies_2021.domain.model.ResultMapper
import com.example.project_movies_2021.presentation.ui.viewmodel.MainViewModel
import com.example.project_movies_2021.presentation.ui.base.BaseFragment
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

        vModel.popularMovies.observe(this,{ response ->
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
                    Handler().postDelayed({
                        displayedChild(2, binding!!.vfMain)
                        showError(response.errorMessage)
                    }, 1000)

                }
            }
        })
    }

    private fun showError(errorMessage: ErrorMessage) {
        binding?.tvError?.text = errorMessage.message
    }

    private fun populateMovie(data: List<ResultMapper>) {
        binding?.tvName?.text = data[1].title
    }


    private fun callPopularMovies(){
        vModel.getPopularMovies()
    }

    override fun onResume() {
        super.onResume()
        vModel.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        vModel.destroy()
        binding = null
    }

}