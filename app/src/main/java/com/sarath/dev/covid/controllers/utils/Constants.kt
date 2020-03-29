package com.sarath.dev.covid.controllers.utils

class Constants {
    companion object {
        const val CONFIRMED: String = "Confirmed"
        const val RECOVERED: String = "Recovered"
        const val DECEASED: String = "Deceased"
        const val API_ENDPOINT = "https://api.covid19api.com/"
        private const val FLAG_ENDPOINT = "https://www.countryflags.io/"

        fun flag(country: String?): String {
            return "$FLAG_ENDPOINT$country/flat/64.png"
        }
    }
}