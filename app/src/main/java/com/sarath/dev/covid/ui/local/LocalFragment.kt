package com.sarath.dev.covid.ui.local

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.sarath.dev.covid.COVID19
import com.sarath.dev.covid.R
import com.sarath.dev.covid.controllers.network.live.LiveDataResponse
import com.sarath.dev.covid.controllers.network.live.LiveMappper
import com.sarath.dev.covid.controllers.utils.GeneralUtil
import com.sarath.dev.covid.controllers.utils.ToastsUtil
import com.sarath.dev.covid.database.entity.CountryEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class LocalFragment : Fragment() {

    private lateinit var localViewModel: LocalViewModel
    private lateinit var recoveredCases: TextView
    private lateinit var confirmedCases: TextView
    private lateinit var deaths: TextView
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var chip: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        localViewModel =
            ViewModelProviders.of(this).get(LocalViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_local, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.local_recycler_view)
        initializeViews(root)
        recyclerView.addOnScrollListener(scrollListener)
        localViewModel.setUpRecyclerView(recyclerView)
        fetchData()
        return root
    }

    private fun initializeViews(root: View) {
        chip = root.findViewById(R.id.chip)
        refreshLayout = root.findViewById(R.id.refresh_layout)
        recoveredCases = root.findViewById(R.id.recovered_cases)
        confirmedCases = root.findViewById(R.id.active_cases)
        deaths = root.findViewById(R.id.deaths)
        chip.text = COVID19.country()
        chip.isEnabled = false
        refreshLayout.setOnRefreshListener {
            fetchData()
        }
    }

    private val scrollListener: RecyclerView.OnScrollListener = object: RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (!recyclerView.canScrollVertically(1)) chip.visibility = View.GONE
            else chip.visibility = View.VISIBLE
        }
    }

    private fun fetchData() {
        refreshLayout.isRefreshing = true
        LiveMappper().fetchLiveData(COVID19.country()!!).enqueue(object:
            Callback<List<LiveDataResponse>> {
            override fun onFailure(call: Call<List<LiveDataResponse>>, t: Throwable) {
                ToastsUtil.d(t.message!!)
            }

            override fun onResponse(
                call: Call<List<LiveDataResponse>>,
                response: Response<List<LiveDataResponse>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    data?.sortedWith(Comparator {o1: LiveDataResponse, o2: LiveDataResponse -> o1.date!!.compareTo(o2.date!!)})
                    setViews(data)
                }
            }

        })

        COVID19.countryDao().getCode(COVID19.country()).observe(this, Observer {
            if (!it.isNullOrEmpty()) localViewModel.fetchData(it[0].toLowerCase(), refreshLayout)
        })
    }

    private fun setViews(data: List<LiveDataResponse>?) {
        if (!data.isNullOrEmpty()) {
            val latestTimestamp = data[data.size - 1].date
            val latestLiveData = ArrayList<LiveDataResponse>()

            for (response in data) {
                if (!response.date.isNullOrEmpty() && response.date == latestTimestamp) latestLiveData.add(response)
            }

            for (latest in latestLiveData) {
                when (latest.status) {
                    "confirmed" -> {
                        confirmedCases.text = latest.cases.toString()
                    }
                    "recovered" -> {
                        recoveredCases.text = latest.cases.toString()
                    }
                    "deaths" -> {
                        deaths.text = latest.cases.toString()
                    }
                }
            }
        }
    }
}