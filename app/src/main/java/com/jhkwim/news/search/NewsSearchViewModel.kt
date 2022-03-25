package com.jhkwim.news.search

import android.net.Uri
import android.text.Html
import android.text.Spanned
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jhkwim.news.api.News
import com.jhkwim.news.repository.NewsRepository
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class NewsSearchViewModel(private val repository: NewsRepository) : ViewModel() {

    companion object {
        val TAG: String = NewsSearchViewModel::class.java.name
    }

    var searchText = MutableLiveData<String>()
    var isEnabledSearchButton = MutableLiveData<Boolean>()
    var searchedNews = MutableLiveData<List<News>>()
    val searchedNewsSize get() = newsList.size
    var uri = MutableLiveData<Uri>()
    val error = MutableLiveData<Error>()

    private val newsList = arrayListOf<News>()

    fun searchTextChanged(text: CharSequence) {
        isEnabledSearchButton.value = text.trim().isNotEmpty()
    }

    fun onSearchButtonClicked(searchString: String) {
        searchNews(searchString)
    }

    private fun searchNews(searchString: String) {
        val size = newsList.size

        if (size > 0) {
            newsList.clear()
        }

        repository.searchNews(searchString).subscribe(
            { resultNews -> addNews(resultNews.items) },
            { throwable -> error.value = Error(throwable) }
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

    fun formatDate(strDate: String): String {
        try {
            val formatterCal = SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z", Locale.US)
            val date = formatterCal.parse(strDate) // all done

            date ?: throw NullPointerException("result of $strDate is null.")

            val formatterStr = SimpleDateFormat("yyyy년 MM월 dd일 (E) / HH:mm:ss", Locale.KOREAN)
            return formatterStr.format(date)
        } catch (e: Exception) {
            Log.e(TAG, e.localizedMessage, e)
        }

        return "unknown"
    }
}