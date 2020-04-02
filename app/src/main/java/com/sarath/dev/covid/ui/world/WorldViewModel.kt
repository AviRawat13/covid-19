package com.sarath.dev.covid.ui.world

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sarath.dev.covid.COVID19
import com.sarath.dev.covid.MainActivity
import com.sarath.dev.covid.controllers.adapters.DataRecyclerAdapter
import com.sarath.dev.covid.controllers.network.summary.SummaryCountryResponse
import com.sarath.dev.covid.controllers.network.summary.SummaryResponse
import com.sarath.dev.covid.controllers.utils.Constants
import com.sarath.dev.covid.controllers.utils.ToastsUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class WorldViewModel : ViewModel() {
    private var dataAdapter: DataRecyclerAdapter? = null
    private var refreshLayout: SwipeRefreshLayout? = null

    fun setUpRecyclerView(recyclerView: RecyclerView) {
        dataAdapter = DataRecyclerAdapter(null)
        recyclerView.adapter = dataAdapter
    }

    fun fetchData() {
        refreshLayout?.isRefreshing = true
        COVID19.i().covidService().getSummary().enqueue(object: Callback<SummaryResponse> {
            override fun onFailure(call: Call<SummaryResponse>, t: Throwable) {
                ToastsUtil.l(t.message!!, COVID19.context!!)
            }

            override fun onResponse(
                call: Call<SummaryResponse>,
                response: Response<SummaryResponse>
            ) {
                if (response.body() != null
                    && response.body()!!.countries != null)
                    if (dataAdapter!!.dataItems != null) {
                        dataAdapter!!.dataItems!!.clear()
                    } else {
                        dataAdapter!!.dataItems = ArrayList()
                    }

                var responses: List<SummaryCountryResponse> = response.body()!!.countries!!
                responses = responses.sortedWith(compareByDescending{ it.totalConfirmed })

                for (summaryDataResponse in responses) {
                    if (!summaryDataResponse.country.isNullOrEmpty()) {
                        dataAdapter!!.dataItems!!.add(summaryDataResponse)
                    }
                }

                refreshLayout?.isRefreshing = false
                dataAdapter!!.notifyDataSetChanged()
            }
        })
    }

    fun setUpRefreshLayoutCallbacks(refreshLayout: SwipeRefreshLayout?) {
        this.refreshLayout = refreshLayout
        refreshLayout?.setOnRefreshListener {
            fetchData()
        }
    }
}