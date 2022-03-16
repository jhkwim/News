package com.jhkwim.news.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.jhkwim.news.api.News
import com.jhkwim.news.repository.NewsRepository

class NewsSearchViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = NewsRepository(application)
    private val newsList = arrayListOf<News>()

    fun getNews(searchString: String) {
        repo.getNews(searchString, 1).subscribe(
            { resultNews ->

                resultNews.items.forEach { news ->
                    Log.i("jhkim", "$news")
                    newsList.add(news)
                }
            },
            { throwable -> Log.e("jhkim", throwable.toString()) }
        )
    }

}