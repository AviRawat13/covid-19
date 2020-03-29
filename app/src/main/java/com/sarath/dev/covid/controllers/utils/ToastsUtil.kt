package com.sarath.dev.covid.controllers.utils

import android.content.Context
import android.widget.Toast
import com.sarath.dev.covid.BuildConfig
import com.sarath.dev.covid.COVID19

class ToastsUtil {
    companion object {
        fun d(message: String?) {
            if (BuildConfig.DEBUG) l(message, COVID19.context!!)
        }

        fun l(message: String?, context: Context) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}