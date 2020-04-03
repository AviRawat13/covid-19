package com.sarath.dev.covid.controllers.network

import com.sarath.dev.covid.controllers.network.news.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsRetrofitService {
    @GET(Endpoints.NEWS)
    fun getNews(@Query("country") country: String,
                @Query("q") query: String,
                @Query("apiKey") key: String): Call<NewsResponse>
}