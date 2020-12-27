package com.example.weatherforecastapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class WeatherForecastApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        const val TOKEN = "pkcDzmoC4qUTjS8a"  // 彩云API调用令牌
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}