package com.sarath.dev.covid.controllers.network.news

import com.sarath.dev.covid.COVID19
import com.sarath.dev.covid.controllers.utils.Constants
import retrofit2.Call

class NewsMapper {
    fun getNews(country: String): Call<NewsResponse> {
        return COVID19.i().newsService().getNews(country, "corona virus", Constants.NEWS_API_KEY)
    }
}