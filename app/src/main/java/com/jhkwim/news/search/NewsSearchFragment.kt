package com.jhkwim.news.search

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jhkwim.news.R
import com.jhkwim.news.databinding.LayoutSearchFragmentBinding
import com.jhkwim.news.repository.NaverNewsRepository
import java.lang.IllegalArgumentException

class NewsSearchFragment : Fragment() {

    private lateinit var binding: LayoutSearchFragmentBinding
    private lateinit var viewModel: NewsSearchViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_search_fragment,
            container,
            false
        )

        viewModel = ViewModelProvider(this, NewsSearchViewModelFactory()).get(NewsSearchViewModel::class.java)

        viewModel.searchedNews.observe(viewLifecycleOwner) { newsList ->
            val adapter = binding.recyclerView.adapter

            adapter ?: return@observe

            if (newsList.isEmpty() && adapter.itemCount > 0) {
                adapter.notifyItemRangeRemoved(0, adapter.itemCount)
            } else {
                val preItemCount = adapter.itemCount
                val newItemCount = newsList.size

                when {
                    preItemCount > newItemCount -> {
                        adapter.notifyItemRangeChanged(0, newItemCount - 1)
                        adapter.notifyItemRangeRemoved(newItemCount, preItemCount - 1)
                    }
                    newItemCount > preItemCount -> {
                        adapter.notifyItemRangeChanged(0, preItemCount - 1)
                        adapter.notifyItemRangeInserted(preItemCount, newItemCount)
                    }
                    else -> {
                        adapter.notifyItemRangeChanged(0, preItemCount - 1)
                    }
                }
            }
        }

        viewModel.uri.observe(viewLifecycleOwner) { uri ->
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }

        binding.newsSearchViewModel = viewModel
        binding.lifecycleOwner = this
        binding.recyclerView.adapter = NewsAdapter(viewModel)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

}