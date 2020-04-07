package com.sarath.dev.covid.controllers.network.live

import com.sarath.dev.covid.COVID19
import com.sarath.dev.covid.controllers.utils.DateUtil
import retrofit2.Call

class LiveMappper {
    fun fetchLiveData(country: String): Call<List<LiveDataResponse>> {
        return COVID19.i().covidService().getLiveDataByStatus(country, DateUtil.current())
    }
}