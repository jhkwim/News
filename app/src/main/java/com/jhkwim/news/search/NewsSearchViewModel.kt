package com.jhkwim.news.search

import android.app.Application
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.text.Spanned
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhkwim.news.api.News
import com.jhkwim.news.repository.NaverNewsRepository
import java.text.SimpleDateFormat
import java.util.*

class NewsSearchViewModel(application: Application) : AndroidViewModel(application) {

    var searchText = MutableLiveData<String>()
    var uri = MutableLiveData<Uri>()

    private val repo = NaverNewsRepository()
    private val newsList = arrayListOf<News>()
    private val newsAdapter = NewsAdapter(this)

    val newsSize get() = newsList.size

    fun init(recyclerView: RecyclerView) {
        recyclerView.adapter = newsAdapter
        recyclerView.layoutManager = LinearLayoutManager(getApplication())
    }

    fun getNews() {
        val size = newsList.size

        if (size > 0) {
            newsList.clear()
            newsAdapter.notifyItemRangeRemoved(0, size)
        }

        val text = searchText.value
        text ?: return

        repo.getNews(text, 1).subscribe(
            { resultNews ->

                resultNews.items.forEach { news ->
                    Log.i("jhkim", "$news")
                    newsList.add(news)

                    Handler(Looper.getMainLooper()).post {
                        newsAdapter.notifyItemInserted(newsList.size - 1)
                    }
                }
            },
            { throwable -> Log.e("jhkim", throwable.toString()) }
        )
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