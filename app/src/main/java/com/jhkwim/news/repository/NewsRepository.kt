package com.jhkwim.news.repository

import com.jhkwim.news.api.ResultNews
import io.reactivex.rxjava3.core.Observable

interface NewsRepository {

    fun searchNews(searchString: String): Observable<ResultNews>

}