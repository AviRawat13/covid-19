package com.sarath.dev.covid.controllers.utils

import android.content.Context
import android.widget.Toast
import com.sarath.dev.covid.BuildConfig

class ToastsUtil {
    companion object {
        fun d(message: String?, context: Context) {
            if (BuildConfig.DEBUG) l(message, context)
        }

        fun l(message: String?, context: Context) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}