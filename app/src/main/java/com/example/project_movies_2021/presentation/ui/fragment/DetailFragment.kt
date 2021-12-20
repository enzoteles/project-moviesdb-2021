package com.example.project_movies_2021.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project_movies_2021.R
import com.example.project_movies_2021.databinding.FragmentDetailBinding
import com.example.project_movies_2021.domain.model.ResultMapper
import com.example.project_movies_2021.presentation.component.image.ImageUtils
import com.example.project_movies_2021.presentation.ui.base.BaseFragment
import com.example.project_movies_2021.presentation.ui.viewmodel.DefaultViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val MOVIE_POPULAR = "MOVIE_POPULAR"


class DetailFragment : BaseFragment<DefaultViewModel, FragmentDetailBinding>() {
    private var result: ResultMapper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            result = it.getSerializable(MOVIE_POPULAR) as ResultMapper
        }
    }


    override fun getViewModel(): DefaultViewModel {
        val baseViewModel: DefaultViewModel by viewModel()
        return baseViewModel
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let { view ->
            view.tvIdValue.text = result?.id.toString()
            view.tvTitleValue.text = result?.title
            view.tvDateValue.text = result?.release_date
            view.tValor.text = result?.vote_average
            view.tvOverviewValue.text = result?.overview

            ImageUtils.loadImage(view.tvImage, result?.backdrop_path, R.drawable.ic_placeholder)

        }
    }


}