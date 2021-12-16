package com.example.project_movies_2021.commons

import android.widget.ViewFlipper
import androidx.fragment.app.Fragment

fun Fragment.displayedChild(value: Int, vf: ViewFlipper) {
    vf.displayedChild = value
}