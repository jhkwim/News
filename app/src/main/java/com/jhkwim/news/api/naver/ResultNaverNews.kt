package com.jhkwim.news.api.naver

data class ResultNaverNews(
    val total: Int = 0,
    val start: Int = 0,
    val display: Int = 0,
    val items: List<NaverNews>
)
