package com.jhkwim.news.api

data class ResultNews(
    val total: Int = 0,
    val start: Int = 0,
    val display: Int = 0,
    val items: List<News>
)
