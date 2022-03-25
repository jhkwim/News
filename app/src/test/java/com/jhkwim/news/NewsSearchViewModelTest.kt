package com.jhkwim.news

import android.net.Uri
import android.text.Html
import android.text.Spanned
import com.jhkwim.news.api.News
import com.jhkwim.news.api.ResultNews
import com.jhkwim.news.repository.NewsRepository
import com.jhkwim.news.search.NewsSearchViewModel
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import io.reactivex.rxjava3.core.Observable

class NewsSearchViewModelTest : BehaviorSpec({

    lateinit var mockNewsRepository: NewsRepository
    lateinit var viewModel: NewsSearchViewModel

    beforeSpec {
        mockNewsRepository = mockk()
        viewModel = NewsSearchViewModel(mockNewsRepository)
    }

    Given("검색어가") {

        When("입력이 되어있으면") {
            viewModel.searchTextChanged("test")
            then("입력버튼이 활성화 된다.") {
                viewModel.isEnabledSearchButton.value shouldBe true
            }
        }

        When("공백이면") {
            viewModel.searchTextChanged("")
            then("입력버튼이 비활성화된다.") {
                viewModel.isEnabledSearchButton.value shouldBe false
            }
        }

        When("띄어쓰기면") {
            viewModel.searchTextChanged(" ")
            then("입력버튼이 비활성화된다.") {
                viewModel.isEnabledSearchButton.value shouldBe false
            }
        }
    }

    Given("검색어를 입력 후") {
        val dummySearchString = "검색어"
        viewModel.searchTextChanged(dummySearchString)

        When("검색 결과를 가져오는 중 에러가 발생한 경우") {
            val throwable = Throwable("에러 발생")
            val expected = Observable.error<ResultNews>(throwable)
            every { mockNewsRepository.searchNews(dummySearchString) } returns expected

            viewModel.onSearchButtonClicked(dummySearchString)

            Then("에러를 전달한다.") {
                viewModel.error.value shouldBe Error(throwable)
            }
        }

        When("검색이 완료된 경우") {
            val dummyNews = News("검색어", "링크", "내용", "Wed, 23 Mar 2022 13:18:00 +0900")
            val dummyList = listOf(dummyNews)

            val expected = Observable.just(ResultNews(dummyList))
            every { mockNewsRepository.searchNews(dummySearchString) } returns expected

            viewModel.onSearchButtonClicked(dummySearchString)

            Then("뉴스 검색 결과를 전달한다.") {
                verify {
                    mockNewsRepository.searchNews(dummySearchString)
                }
                viewModel.searchedNews.value shouldBe dummyList
            }

            Then("뉴스 검색 결과의 타이틀을 가져온다.") {
                mockkStatic(Html::class)
                val spannedMock = mockk<Spanned>()
                every {
                    Html.fromHtml(
                        dummyNews.title,
                        Html.FROM_HTML_MODE_LEGACY
                    )
                } returns spannedMock

                viewModel.getTile(0) shouldBe spannedMock
            }

            Then("뉴스 검색 결과의 게시 날짜를 가져온다.") {
                val date = viewModel.getDate(0)
                date shouldBe viewModel.formatDate(dummyNews.pubDate)
            }

            Then("뉴스 검색 결과의 링크주소를 가져온다.") {
                mockkStatic(Uri::class)
                val uriMock = mockk<Uri>()
                every { Uri.parse(dummyNews.link) } returns uriMock

                viewModel.toUri(0)
                viewModel.uri.value shouldBe uriMock
            }
        }
    }
})