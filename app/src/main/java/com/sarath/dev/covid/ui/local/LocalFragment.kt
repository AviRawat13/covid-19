package com.sarath.dev.covid.ui.local

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
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
    private lateinit var countries: List<CountryEntity>
    private lateinit var recoveredCases: TextView
    private lateinit var confirmedCases: TextView
    private lateinit var deaths: TextView


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
        localViewModel.setUpRecyclerView(recyclerView)
        fetchData()
        return root
    }

    private fun initializeViews(root: View) {
        recoveredCases = root.findViewById(R.id.recovered_cases)
        confirmedCases = root.findViewById(R.id.active_cases)
        deaths = root.findViewById(R.id.deaths)
        activity?.title = GeneralUtil.title(COVID19.country())
    }

    private fun fetchData() {
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