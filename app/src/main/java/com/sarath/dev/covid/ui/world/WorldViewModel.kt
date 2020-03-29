package com.sarath.dev.covid.ui.world

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sarath.dev.covid.COVID19
import com.sarath.dev.covid.controllers.adapters.DataRecyclerAdapter
import com.sarath.dev.covid.controllers.network.summary.SummaryCountryResponse
import com.sarath.dev.covid.controllers.network.summary.SummaryResponse
import com.sarath.dev.covid.controllers.utils.ToastsUtil
import com.sarath.dev.covid.database.COVIDRoomDatabase
import com.sarath.dev.covid.database.dao.LocalDao
import com.sarath.dev.covid.database.entity.LocalEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class WorldViewModel : ViewModel() {
    private var dataAdapter: DataRecyclerAdapter? = null
    private var refreshLayout: SwipeRefreshLayout? = null
    private val location: String? = Locale.getDefault().displayCountry
    private val localDao: LocalDao = COVIDRoomDatabase.getDatabase(COVID19.context!!).localDao()

    fun setUpRecyclerView(recyclerView: RecyclerView) {
        dataAdapter = DataRecyclerAdapter(null)
        recyclerView.adapter = dataAdapter
    }

    fun fetchData() {
        refreshLayout?.isRefreshing = true
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

                var responses: List<SummaryCountryResponse> = response.body()!!.countries!!
                responses = responses.sortedWith(compareByDescending{ it.totalConfirmed })

                for (summaryDataResponse in responses) {
                    if (!summaryDataResponse.country.isNullOrEmpty()) {
                        dataAdapter!!.dataItems!!.add(summaryDataResponse)

                        val country = summaryDataResponse.country!!.split("(")[0].toLowerCase()
                        if (country == COVID19.country()) {
                            GlobalScope.launch {
                                insert(summaryDataResponse)
                            }
                        }
                    }
                }

                dataAdapter!!.notifyDataSetChanged()
                refreshLayout?.isRefreshing = false
            }

        })
    }

    suspend fun insert(response: SummaryCountryResponse) {
        localDao.insert(
            LocalEntity(
                1,
                response.country,
                response.countrySlug,
                response.totalConfirmed,
                response.newConfirmed,
                response.totalRecovered,
                response.newRecovered,
                response.totalDeaths,
                response.newDeaths
            )
        )
    }

    fun setUpRefreshLayoutCallbacks(refreshLayout: SwipeRefreshLayout?) {
        this.refreshLayout = refreshLayout
        refreshLayout?.setOnRefreshListener {
            fetchData()
        }
    }
}