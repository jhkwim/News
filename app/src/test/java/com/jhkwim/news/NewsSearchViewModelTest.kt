package com.jhkwim.news

import android.net.Uri
import com.jhkwim.news.api.News
import com.jhkwim.news.api.ResultNews
import com.jhkwim.news.repository.NewsRepository
import com.jhkwim.news.search.NewsSearchViewModel
import com.jhkwim.news.utils.LiveDataUtils.getOrAwaitValue
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import io.reactivex.rxjava3.core.Observable

class NewsSearchViewModelTest : StringSpec({

    lateinit var mockNewsRepository: NewsRepository
    lateinit var viewModel: NewsSearchViewModel

    val dummySearchString = "네이버"
    val dummyNews = News("title", "link", "description", "pubDate")
    val dummyList = listOf(dummyNews)

    beforeSpec {
        mockNewsRepository = mockk()
        viewModel = NewsSearchViewModel(mockNewsRepository)
    }

    "검색버튼 활성화/비활성화 테스트" {
        viewModel.searchText.value = null

        viewModel.isSearchButtonEnabled() shouldBe false

        viewModel.searchText.value = dummySearchString

        viewModel.isSearchButtonEnabled() shouldBe true
    }

//    "검색버튼 테스트" {
//
//
//        viewModel.onSearchButtonClicked(mockk())
//
//
//    }
//
//    "뉴스 검색 후 searchedNews에 결과값이 반영 되는가" {
//        val searchString = "네이버"
//
//        viewModel.searchText.value = searchString
//
//        val expected = Observable.just(ResultNews(dummyList))
//
//        every { mockNewsRepository.getNews(searchString) } returns expected
//
////        viewModel.getNews()
//
//        verify(exactly = 1) {
//            mockNewsRepository.getNews(searchString)
//        }
//
//        viewModel.searchedNews.value shouldBe dummyList
//    }

    "링크" {
        mockkStatic(Uri::class)
        val uriMock = mockk<Uri>()
        every { Uri.parse(dummyNews.link) } returns uriMock

        viewModel.toUri(0)
        viewModel.uri.value shouldBe uriMock

    }
})