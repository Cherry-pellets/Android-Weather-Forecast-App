package com.example.weatherforecastapp.logic.network

import com.example.weatherforecastapp.logic.model.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @POST("user/registered")
    fun register(@Body data: User): Call<ResponseBody>

//    @FormUrlEncoded
//    @Headers("Content-Type: application/json")
    @POST("user/login")
    fun login(@Body data: User): Call<ResponseBody>

    @POST("user/motifyInformation")
    fun modifyUserInfo(@Body data: User): Call<ResponseBody>
}