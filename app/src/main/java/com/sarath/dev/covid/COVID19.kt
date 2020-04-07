package com.sarath.dev.covid

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.app.AlertDialog.Builder
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.gson.GsonBuilder
import com.sarath.dev.covid.controllers.network.COVIDRetrofitService
import com.sarath.dev.covid.controllers.network.CountryRetrofitService
import com.sarath.dev.covid.controllers.network.NewsRetrofitService
import com.sarath.dev.covid.controllers.utils.Constants
import com.sarath.dev.covid.database.COVIDRoomDatabase
import com.sarath.dev.covid.database.dao.CountryDao
import com.sarath.dev.covid.database.dao.LocalDao
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.*
import java.util.concurrent.TimeUnit

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity

class COVID19: Application() {
    private var covidRetrofitAdapter: Retrofit? = null
    private var countryRetrofitAdapter: Retrofit? = null
    private var newsRetrofitAdapter: Retrofit? = null

    private var covidRetrofitService: COVIDRetrofitService? = null
    private var countryRetrofitService: CountryRetrofitService? = null
    private var newsRetrofitService: NewsRetrofitService? = null

    companion object {
        var instance: COVID19? = null
        var context: Context? = null

        fun i(): COVID19 {
            if (instance == null) instance = COVID19()
            return instance!!
        }

        fun country(): String? {
            if (ContextCompat.checkSelfPermission(
                    context!!,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val locationManager = (context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
                var lm: Location? = null
                var bestLocation: Location? = null
                for (provider in locationManager.allProviders) {
                    lm = locationManager.getLastKnownLocation(provider)
                    if (lm == null) continue
                    if (bestLocation == null || lm.accuracy < bestLocation.accuracy) {
                        bestLocation = lm
                    }
                }

                val gcd = Geocoder(context!!, Locale.getDefault())
                val addresses: List<Address>
                try {
                    addresses = gcd.getFromLocation(lm!!.latitude, lm!!.longitude, 1)
                    if (addresses.isNotEmpty()) return addresses[0].countryName.toLowerCase()
                } catch (e1: Exception) {
                    e1.printStackTrace()
                }
            } else {

                ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), Constants.REQUEST_CODE_LOCATION)
            }

            return ""
        }

        fun localDao(): LocalDao {
            return COVIDRoomDatabase.getDatabase(context!!).localDao()
        }

        fun countryDao(): CountryDao {
            return COVIDRoomDatabase.getDatabase(context!!).countryDao()
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        setUpCOVIDRetrofit()
        setUpCountryRetrofit()
        setUpNewsRetrofit()
    }

    private fun setUpCountryRetrofit() {
        val gson = GsonBuilder().setLenient().create()
        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(Constants.COUNTRY_API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(getHttpClient()!!)
        countryRetrofitAdapter = builder.build()
        countryRetrofitService = countryRetrofitAdapter!!.create(CountryRetrofitService::class.java)
    }

    private fun setUpCOVIDRetrofit() {
        val gson = GsonBuilder().setLenient().create()
        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(Constants.COVID_API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(getHttpClient()!!)
        covidRetrofitAdapter = builder.build()
        covidRetrofitService = covidRetrofitAdapter!!.create(COVIDRetrofitService::class.java)
    }

    private fun setUpNewsRetrofit() {
        val gson = GsonBuilder().setLenient().create()
        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(Constants.NEWS_API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(getHttpClient()!!)
        newsRetrofitAdapter = builder.build()
        newsRetrofitService = newsRetrofitAdapter!!.create(NewsRetrofitService::class.java)
    }

    fun covidService(): COVIDRetrofitService {
        if (covidRetrofitService == null) setUpCOVIDRetrofit()
        return covidRetrofitService!!
    }

    fun countryService(): CountryRetrofitService {
        if (countryRetrofitService == null) setUpCountryRetrofit()
        return countryRetrofitService!!
    }

    fun newsService(): NewsRetrofitService {
        if (newsRetrofitService == null) setUpNewsRetrofit()
        return newsRetrofitService!!
    }

    private fun getHttpClient(): OkHttpClient? {
        try {
            return OkHttpClient.Builder()
                .readTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return null
    }
}