package com.example.weatherforecastapp.logic.dao


import android.content.Context
import androidx.core.content.edit
import com.example.weatherforecastapp.WeatherForecastApplication
import com.example.weatherforecastapp.logic.model.Place
import com.google.gson.Gson

object PlaceDao {
    fun savePlace(place: Place) {
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace() :Place {
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() = WeatherForecastApplication.context.getSharedPreferences("weather_forecast", Context.MODE_PRIVATE)

}