package com.jhkwim.news.repository

import com.jhkwim.news.api.News
import com.jhkwim.news.api.ResultNews
import com.jhkwim.news.api.naver.NaverAPI
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class NaverNewsRepository : NewsRepository {

    private val naverAPI = NaverAPI.create()

    override fun getNews(searchString: String): Observable<ResultNews> =
        naverAPI.searchNews(searchString, 100, 1, NaverAPI.SORT_SIM)
            .subscribeOn(Schedulers.io())
            .map { resultNaverNews ->
                val newsList = arrayListOf<News>()
                resultNaverNews.items.forEach {
                    newsList.add(News(it.title, it.originallink, it.description, it.pubDate))
                }
                ResultNews(newsList)
            }
            .observeOn(AndroidSchedulers.mainThread())
}