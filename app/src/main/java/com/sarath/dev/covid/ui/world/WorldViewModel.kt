package com.sarath.dev.covid.ui.world

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.sarath.dev.covid.COVID19
import com.sarath.dev.covid.controllers.adapters.DataRecyclerAdapter
import com.sarath.dev.covid.controllers.network.summary.SummaryResponse
import com.sarath.dev.covid.controllers.utils.ToastsUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorldViewModel : ViewModel() {
    private var dataAdapter: DataRecyclerAdapter? = null

    fun setUpRecyclerView(recyclerView: RecyclerView) {
        dataAdapter = DataRecyclerAdapter(null)
        recyclerView.adapter = dataAdapter
    }

    fun fetchData() {
        COVID19.i().service().getSummary().enqueue(object: Callback<SummaryResponse> {
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

                    for (summaryDataResponse in response.body()!!.countries!!) {
                        if (summaryDataResponse.country != null) {
                                dataAdapter!!.dataItems!!.add(summaryDataResponse)
                        }
                    }
                    dataAdapter!!.notifyDataSetChanged()
            }

        })
    }
}