package com.jhkwim.news.search

import androidx.recyclerview.widget.RecyclerView
import com.jhkwim.news.databinding.LayoutNewsViewBinding

class NewsViewHolder(
    val binding: LayoutNewsViewBinding,
    private val viewModel: NewsSearchViewModel
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(position: Int) {
        binding.viewModel = viewModel
        binding.position = position
        binding.executePendingBindings()
    }

}