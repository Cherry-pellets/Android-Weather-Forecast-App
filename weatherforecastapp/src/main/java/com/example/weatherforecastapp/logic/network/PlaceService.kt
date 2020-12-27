package com.example.weatherforecastapp.logic.network

import com.example.weatherforecastapp.WeatherForecastApplication
import com.example.weatherforecastapp.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("v2/place?tokent=${WeatherForecastApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String) : Call<PlaceResponse> // 将返回的json解析成PlaceResponse对象

}