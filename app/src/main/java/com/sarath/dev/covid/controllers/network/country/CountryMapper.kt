package com.sarath.dev.covid.controllers.network.country

import com.sarath.dev.covid.COVID19
import com.sarath.dev.covid.controllers.utils.ToastsUtil
import com.sarath.dev.covid.database.entity.CountryEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountryMapper {
    fun fetchCountries() {
        val call = COVID19.i().countryService().getCountries()
        call.enqueue(object: Callback<Map<String, String>> {
            override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                ToastsUtil.d(t.message!!)
            }

            override fun onResponse(
                call: Call<Map<String, String>>,
                response: Response<Map<String, String>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    for (entry in response.body()!!.entries) {
                        GlobalScope.launch {
                            insert(entry.key.toLowerCase(), entry.value.toLowerCase())
                        }
                    }
                }
            }

        })
    }

    suspend fun insert(key:String, value:String) {
            COVID19.countryDao().insert(CountryEntity(
            value,
            "",
            key
        ))
    }
}