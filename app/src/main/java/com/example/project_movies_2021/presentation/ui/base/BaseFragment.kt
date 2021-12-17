package com.example.project_movies_2021.presentation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment <VM: ViewModel, B: ViewBinding>: Fragment(){

    protected var binding: B ?= null
    protected lateinit var vModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getFragmentBinding(inflater, container)
        vModel = getViewModel()
        return binding?.root
    }

    abstract fun getViewModel() : VM
    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) : B
}