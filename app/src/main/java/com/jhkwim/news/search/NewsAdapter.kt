package com.jhkwim.news.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jhkwim.news.R
import com.jhkwim.news.databinding.LayoutSearchFragmentBinding

class NewsAdapter(val viewModel: NewsSearchViewModel): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutSearchFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class ViewHolder(val binding: LayoutSearchFragmentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.newsSearchViewModel = viewModel
            binding.executePendingBindings()
        }
    }
}