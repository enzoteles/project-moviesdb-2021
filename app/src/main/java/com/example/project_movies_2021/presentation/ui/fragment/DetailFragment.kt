package com.example.project_movies_2021.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project_movies_2021.R
import com.example.project_movies_2021.domain.model.MoviesPopularMapper
import com.example.project_movies_2021.domain.model.ResultMapper

private const val MOVIE_POPULAR = "MOVIE_POPULAR"


class DetailFragment : Fragment() {
    private var result: ResultMapper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            result = it.getSerializable(MOVIE_POPULAR) as ResultMapper
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("result", "${result?.title}")
    }
}