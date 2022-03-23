package com.jhkwim.news.repository

import com.jhkwim.news.api.ResultNews
import io.reactivex.rxjava3.core.Observable

interface NewsRepository {

    fun getNews(searchString: String): Observable<ResultNews>

}