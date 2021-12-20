package com.example.project_movies_2021.presentation.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_movies_2021.R
import com.example.project_movies_2021.commons.ApiResponse
import com.example.project_movies_2021.commons.ErrorMessage
import com.example.project_movies_2021.commons.displayedChild
import com.example.project_movies_2021.databinding.FragmentMainBinding
import com.example.project_movies_2021.domain.model.ResultMapper
import com.example.project_movies_2021.presentation.component.DefaultViewHolderKotlin
import com.example.project_movies_2021.presentation.component.DefaultViewListAdapter
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
                        showLoading()
                }
                is ApiResponse.Success->{
                    Handler().postDelayed({
                        populateMovie(response.data.results.map { it.toResultMapper() })
                    }, 1000)
                }
                is ApiResponse.Failure->{
                    Handler().postDelayed({
                        showError(response.errorMessage)
                    }, 1000)

                }
            }
        })
    }

    fun showLoading() {
        displayedChild(0, binding!!.vfMain)
    }

    fun showError(errorMessage: ErrorMessage) {
        displayedChild(2, binding!!.vfMain)
        binding?.tvError?.text = errorMessage.message
    }

    fun populateMovie(data: List<ResultMapper>) {


        displayedChild(1, binding!!.vfMain)


        binding?.rvMoviePopular?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvMoviePopular?.setHasFixedSize(true)
        val adapter = DefaultViewListAdapter(data, R.layout.item_movie_popular)
        adapter.setBindViewHolderCallback(object: DefaultViewListAdapter.OnBindViewHolder<ResultMapper> {
            override fun onBind(item: ResultMapper, holder: DefaultViewHolderKotlin) {
                holder.mView.findViewById<TextView>(R.id.tvTitle).text = item.title
                holder.mView.findViewById<TextView>(R.id.tvSubtitle).text = item.overview
            }
        })

        binding?.rvMoviePopular?.adapter = adapter



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