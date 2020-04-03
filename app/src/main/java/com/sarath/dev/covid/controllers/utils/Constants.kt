package com.sarath.dev.covid.controllers.utils

class Constants {
    companion object {
        const val REQUEST_CODE_LOCATION: Int = 1001

        const val CONFIRMED: String = "Confirmed"
        const val RECOVERED: String = "Recovered"
        const val DECEASED: String = "Deceased"
        const val COVID_API_ENDPOINT = "https://corona-stats-app.herokuapp.com/api/"
        const val COUNTRY_API_ENDPOINT = "http://country.io/"
        const val NEWS_API_ENDPOINT = "https://newsapi.org/v2/"
        const val NEWS_API_KEY = "00f85cd5105d43a384a947cadfd21525"
    }
}