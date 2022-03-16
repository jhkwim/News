package com.jhkwim.news.repository

import android.app.Application
import com.jhkwim.news.api.NaverAPI
import com.jhkwim.news.api.ResultNews
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class NewsRepository(application: Application) {

    val naverAPI = NaverAPI.create()

    fun getNews(searchString: String, start: Int): Observable<ResultNews> = naverAPI.searchNews(searchString, 100, start)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())


}