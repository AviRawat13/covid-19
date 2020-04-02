package com.sarath.dev.covid.controllers.network

import com.sarath.dev.covid.controllers.network.live.LiveDataResponse
import com.sarath.dev.covid.controllers.network.summary.SummaryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface COVIDRetrofitService {
    @GET(Endpoints.SUMMARY)
    fun getSummary(): Call<SummaryResponse>

    @GET(Endpoints.LIVE_DATA)
    fun getLiveDataByStatus(@Query("country") country: String, @Query("timestamp") timestamp: String): Call<List<LiveDataResponse>>
}