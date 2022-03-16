package com.jhkwim.news.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class NewsSearchFragment: Fragment() {

    private lateinit var viewModel: NewsSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(NewsSearchViewModel::class.java)
        viewModel.getNews("코로나")

        return super.onCreateView(inflater, container, savedInstanceState)
    }

}