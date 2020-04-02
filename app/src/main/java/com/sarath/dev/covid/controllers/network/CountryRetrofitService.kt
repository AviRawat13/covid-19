package com.sarath.dev.covid.controllers.network

import retrofit2.Call
import retrofit2.http.GET

interface CountryRetrofitService {
    @GET(Endpoints.COUNTRY_CODE)
    fun getCountries(): Call<Map<String, String>>
}