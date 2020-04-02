package com.sarath.dev.covid.controllers.network.summary

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SummaryCountryResponse(
    @SerializedName("Country")
    @Expose var country: String?, @SerializedName("CountrySlug")
    @Expose var countrySlug: String?, @SerializedName("NewConfirmed")
    @Expose var newConfirmed: Int?, @SerializedName("TotalConfirmed")
    @Expose var totalConfirmed: Int?, @SerializedName("NewDeaths")
    @Expose var newDeaths: Int?, @SerializedName("TotalDeaths")
    @Expose var totalDeaths: Int?, @SerializedName("NewRecovered")
    @Expose var newRecovered: Int?, @SerializedName("TotalRecovered")
    @Expose var totalRecovered: Int?, @SerializedName("flagURL")
    @Expose var flagURL: String?, @SerializedName("code")
    @Expose var code: String?
)
