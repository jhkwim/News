package com.jhkwim.news.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jhkwim.news.R
import com.jhkwim.news.databinding.LayoutSearchFragmentBinding

class NewsSearchFragment : Fragment() {

    private lateinit var viewModel: NewsSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = DataBindingUtil.inflate<LayoutSearchFragmentBinding>(
            inflater,
            R.layout.layout_search_fragment,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(NewsSearchViewModel::class.java)
        viewModel.init(binding.recyclerView)
        viewModel.uri.observe(viewLifecycleOwner) { uri ->
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }

        binding.newsSearchViewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

}