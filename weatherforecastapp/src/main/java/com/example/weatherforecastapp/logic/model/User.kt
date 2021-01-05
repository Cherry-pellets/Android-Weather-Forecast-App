package com.example.weatherforecastapp.logic.model

import java.io.Serializable
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName



class User (val password: String, val name: String, val nickname: String, val profile: String):Serializable
class Holder {
    @SerializedName(value = "status")
    var status: Int = 0
    @SerializedName(value = "message")
    var message: String? = null
    @SerializedName(value = "data")
    var data: User? = null
}