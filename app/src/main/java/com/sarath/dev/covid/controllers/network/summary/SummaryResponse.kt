package com.sarath.dev.covid.controllers.network.summary

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SummaryResponse(
    @SerializedName("Date")
    @Expose var date: String?, @SerializedName("Countries")
    @Expose var countries: List<SummaryCountryResponse>?
)