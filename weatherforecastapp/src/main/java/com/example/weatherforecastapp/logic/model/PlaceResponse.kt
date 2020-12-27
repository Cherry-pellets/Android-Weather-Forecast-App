package com.example.weatherforecastapp.logic.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(val status: String, val places: List<Place>)
data class Place(val name: String, val loation: Location,
                 @SerializedName("formatted_adddress") val address:String)
data class Location(val lng: String, val lat: String)