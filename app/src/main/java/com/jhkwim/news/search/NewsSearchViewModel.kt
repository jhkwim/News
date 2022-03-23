package com.jhkwim.news.search

import android.net.Uri
import android.text.Html
import android.text.Spanned
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jhkwim.news.api.News
import com.jhkwim.news.repository.NewsRepository
import java.text.SimpleDateFormat
import java.util.*

class NewsSearchViewModel(private val repository: NewsRepository) : ViewModel() {

    var searchText = MutableLiveData<String>()
    var uri = MutableLiveData<Uri>()
    var searchedNews = MutableLiveData<List<News>>()
    val searchedNewsSize get() = newsList.size

    private val newsList = arrayListOf<News>()

    fun getNews() {
        val size = newsList.size

        if (size > 0) {
            newsList.clear()
        }

        val text = searchText.value
        text ?: return

        repository.getNews(text).subscribe(
            { resultNews ->
                addNews(resultNews.items)
            },
            { throwable -> Log.e("jhkim", throwable.toString()) }
        )
    }

    fun addNews(news: List<News>) {
        newsList.addAll(news)
        searchedNews.value = newsList
    }

    fun addNews(news: News) {
        newsList.add(news)
        searchedNews.value = newsList
    }

    fun toUri(position: Int) {
        uri.value = Uri.parse(newsList[position].link)
    }

    fun getTile(position: Int): Spanned {
        return Html.fromHtml(newsList[position].title, Html.FROM_HTML_MODE_LEGACY)
    }

    fun getDate(position: Int) = formatDate(newsList[position].pubDate)

    private fun formatDate(strDate: String): String {
        val formatterCal = SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z", Locale.US)
        val date = formatterCal.parse(strDate) // all done

        date ?: return "unknown"

        val formatterStr = SimpleDateFormat("yyyy년 MM월 dd일 (E) / HH:mm:ss", Locale.KOREAN)
        return formatterStr.format(date)
    }
}