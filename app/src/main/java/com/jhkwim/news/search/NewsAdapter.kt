package com.jhkwim.news.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jhkwim.news.databinding.LayoutNewsViewBinding

class NewsAdapter(val viewModel: NewsSearchViewModel): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutNewsViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = viewModel.newsSize

    inner class ViewHolder(private val binding: LayoutNewsViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.viewModel = viewModel
            binding.position = position
            binding.executePendingBindings()
        }
    }
}