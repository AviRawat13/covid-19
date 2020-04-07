package com.sarath.dev.covid.controllers.network.live

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LiveDataResponse {
    @SerializedName("Country")
    @Expose
    var country: String? = null

    @SerializedName("Province")
    @Expose
    var province: Any? = null

    @SerializedName("Date")
    @Expose
    var date: String? = null

    @SerializedName("Lat")
    @Expose
    var lat: Double? = null

    @SerializedName("Long")
    @Expose
    var long: Double? = null

    @SerializedName("Confirmed")
    @Expose
    var confirmed: Int? = null

    @SerializedName("Recovered")
    @Expose
    var recovered: Int? = null

    @SerializedName("Deaths")
    @Expose
    var deaths: Int? = null

    @SerializedName("Active")
    @Expose
    var active: Int? = null
}