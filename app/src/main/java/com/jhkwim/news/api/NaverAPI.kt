package com.jhkwim.news.api

import io.reactivex.rxjava3.core.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverAPI {

    companion object {
        private const val BASE_URL = "https://openapi.naver.com"
        private const val CLIENT_ID = "UFVGbXNDH9w14q4tdHx1"
        private const val CLIENT_SECRET = "WvU4bZZmII"

        fun create(): NaverAPI {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val headerInterceptor = Interceptor {
                val request = it.request()
                    .newBuilder()
                    .addHeader("X-Naver-Client-Id", CLIENT_ID)
                    .addHeader("X-Naver-Client-Secret", CLIENT_SECRET)
                    .build()

                return@Interceptor it.proceed(request)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(headerInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(NaverAPI::class.java)
        }
    }

    @GET("v1/search/news.json")
    fun searchNews(
        @Query("query") query: String,
        @Query("display") display: Int,
        @Query("start") start: Int
    ): Observable<ResultNews>

}