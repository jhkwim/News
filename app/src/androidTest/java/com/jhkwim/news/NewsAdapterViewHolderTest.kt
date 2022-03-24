package com.jhkwim.news

import android.view.LayoutInflater
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jhkwim.news.api.News
import com.jhkwim.news.databinding.LayoutNewsViewBinding
import com.jhkwim.news.repository.NaverNewsRepository
import com.jhkwim.news.search.NewsSearchViewModel
import com.jhkwim.news.search.NewsViewHolder
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsAdapterViewHolderTest {

    lateinit var viewHolder: NewsViewHolder
    lateinit var viewModel: NewsSearchViewModel

    @Before
    @UiThreadTest
    fun init() {
        val view = LayoutNewsViewBinding.inflate(
            LayoutInflater.from(InstrumentationRegistry.getInstrumentation().targetContext),
            null,
            false
        )
        viewModel = NewsSearchViewModel(NaverNewsRepository())
        viewHolder = NewsViewHolder(view)
    }

    @Test
    fun bind() {
        viewModel.addNews(
            News(
                "title",
                "link",
                "description",
                "Mon, 21 Mar 2022 15:33:00 +0900"
            )
        )
        viewHolder.bind(0)
        Assert.assertEquals(viewHolder.binding.newsTitle.text.toString(), "title")
        Assert.assertEquals(viewHolder.binding.newsDate.text, "2022년 03월 21일 (월) / 06:33:00")
    }
}