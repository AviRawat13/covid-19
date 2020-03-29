package com.sarath.dev.covid

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.gson.GsonBuilder
import com.sarath.dev.covid.controllers.network.RetrofitService
import com.sarath.dev.covid.controllers.utils.Constants
import com.sarath.dev.covid.database.COVIDRoomDatabase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.jar.Manifest


class COVID19: Application() {
    private var retrofitAdapter: Retrofit? = null
    private var retrofitService: RetrofitService? = null

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
                val locationManager =
                    (context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
                val lm = locationManager.getLastKnownLocation(locationManager.getProvider("gps").name)
                val gcd = Geocoder(context!!, Locale.getDefault())
                val addresses: List<Address>
                try {
                    addresses = gcd.getFromLocation(lm.latitude, lm.longitude, 1)
                    if (addresses.isNotEmpty()) return addresses[0].countryName
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }
            } else {

                ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), Constants.REQUEST_CODE_LOCATION)
            }

            return ""
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        setUpRetrofit()
    }

    private fun setUpRetrofit() {
        val gson = GsonBuilder().setLenient().create()
        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(Constants.API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(getHttpClient()!!)
        retrofitAdapter = builder.build()
        retrofitService = retrofitAdapter!!.create(RetrofitService::class.java)
    }

    fun service(): RetrofitService {
        if (retrofitService == null) setUpRetrofit()
        return retrofitService!!
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