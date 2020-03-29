package com.sarath.dev.covid.controllers.network

import com.sarath.dev.covid.controllers.network.summary.SummaryResponse
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {
    @GET(Endpoints.SUMMARY)
    fun getSummary(): Call<SummaryResponse>
}