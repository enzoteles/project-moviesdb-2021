package com.example.project_movies_2021.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.project_movies_2021.R
import com.example.project_movies_2021.data.remote.Result

class MoviePopularAdapter : PagingDataAdapter<Result, MoviePopularAdapter.ResultViewHolder>(DiffUtilCallBack()){

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
       val inflate = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_popular, parent, false)
        return ResultViewHolder(inflate)
    }

    class ResultViewHolder(inflate: View): RecyclerView.ViewHolder(inflate){
        var title : TextView = inflate.findViewById(R.id.tvTitle)
        var subtitle : TextView = inflate.findViewById(R.id.tvSubtitle)
        fun bind(item: Result?) {
            title.text = item?.title
           subtitle.text = item?.overview
        }

    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }
}


