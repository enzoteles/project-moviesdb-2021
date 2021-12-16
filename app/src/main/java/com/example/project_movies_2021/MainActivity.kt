package com.example.project_movies_2021

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.project_movies_2021.databinding.ActivityMainBinding
import com.example.project_movies_2021.presentation.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)


        binding.btnOk.setOnClickListener {
            callPopularMovies()
        }

        mainViewModel.popularMovies.observe(this,{ popularMovies ->
            popularMovies.forEach {
                Log.i("PopularMovie", it.title)
            }
        })

        setContentView(binding.root)
    }

    private fun callPopularMovies(){
        GlobalScope.launch {
            mainViewModel.getPopularMovies()
        }
    }
}