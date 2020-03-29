package com.sarath.dev.covid

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.sarath.dev.covid.controllers.network.RetrofitService
import com.sarath.dev.covid.controllers.utils.Constants
import com.sarath.dev.covid.database.COVIDRoomDatabase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit


class COVID19: Application() {
    private var db: COVIDRoomDatabase? = null
    private var retrofitAdapter: Retrofit? = null
    private var retrofitService: RetrofitService? = null

    companion object {
        var instance: COVID19? = null
        var context: Context? = null

        fun i(): COVID19 {
            if (instance == null) instance = COVID19()
            return instance!!
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