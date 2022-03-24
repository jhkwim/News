package com.jhkwim.news.search

import androidx.recyclerview.widget.RecyclerView
import com.jhkwim.news.databinding.LayoutNewsViewBinding

class NewsViewHolder(val binding: LayoutNewsViewBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(position: Int) {
        binding.position = position
        binding.executePendingBindings()
    }
}