package com.jhkwim.news.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jhkwim.news.repository.NaverNewsRepository

class NewsSearchViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NewsSearchViewModel::class.java)) {
            NewsSearchViewModel(NaverNewsRepository()) as T
        } else {
            throw IllegalArgumentException()
        }
    }

}