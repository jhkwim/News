package com.jhkwim.news.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jhkwim.news.databinding.LayoutNewsViewBinding

class NewsAdapter(private val viewModel: NewsSearchViewModel) :
    RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutNewsViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(view, viewModel)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = viewModel.searchedNewsSize
}