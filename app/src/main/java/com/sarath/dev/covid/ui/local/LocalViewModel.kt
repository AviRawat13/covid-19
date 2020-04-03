package com.sarath.dev.covid.ui.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sarath.dev.covid.COVID19
import com.sarath.dev.covid.controllers.adapters.DataRecyclerAdapter
import com.sarath.dev.covid.controllers.adapters.NewsAdapter
import com.sarath.dev.covid.controllers.network.news.NewsArticlesResponse
import com.sarath.dev.covid.controllers.network.news.NewsMapper
import com.sarath.dev.covid.controllers.network.news.NewsResponse
import com.sarath.dev.covid.controllers.utils.ToastsUtil
import com.sarath.dev.covid.database.COVIDRoomDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocalViewModel : ViewModel() {
    private var dataAdapter: NewsAdapter? = null

    fun setUpRecyclerView(recyclerView: RecyclerView) {
        dataAdapter = NewsAdapter()
        recyclerView.adapter = dataAdapter
    }

    fun fetchData(country: String, refreshLayout: SwipeRefreshLayout) {
        NewsMapper().getNews(country).enqueue(object: Callback<NewsResponse> {
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                refreshLayout.isRefreshing = false
                ToastsUtil.d(t.message!!)
            }

            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful && response.body() != null && !response.body()!!.articles.isNullOrEmpty()) {
                    refreshLayout.isRefreshing = false
                    NewsAdapter.items!!.clear()
                    NewsAdapter.items = ArrayList(response.body()!!.articles!!)
                    dataAdapter!!.notifyDataSetChanged()
                }
            }

        })
    }
}