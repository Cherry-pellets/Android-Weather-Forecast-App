package com.example.weatherforecastapp.logic

import androidx.lifecycle.liveData
import com.example.weatherforecastapp.logic.model.Place
import com.example.weatherforecastapp.logic.network.WeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

// 仓库层:判断调用方请求地数据应该是从本地数据源获取还是从网络数据源获取，并将数据返回给调用方
object Repository {
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = WeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}