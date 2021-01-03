package com.example.weatherforecastapp.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.weatherforecastapp.logic.Repository
import com.example.weatherforecastapp.logic.model.Place

// ViewModel层
class PlaceViewModel : ViewModel() {
//    记录选中的城市
    fun savePlace(place: Place) = Repository.savePlace(place)
    fun getSavedPlace() = Repository.getSavedPlace()
    fun isPlaceSaved() = Repository.isPlaceSaved()

    private val searchLiveDate = MutableLiveData<String>()

    val placeList = ArrayList<Place>() // 用于对界面上显示的城市数据进行缓存

    val placeLiveData = Transformations.switchMap(searchLiveDate) { query ->
        Repository.searchPlaces(query)
    }

    fun  searchPlaces(query: String) {
        searchLiveDate.value = query
    }
//    外部调用searchPlaces，就会自动调用placeLiveData，placeLiveData里又会调用Repository.searchPlaces，
//    然后将Repository.searchPlaces返回的数据（LiveData）转换成可供Activity观察的LiveData对象
//    《第一行代码Android》第4版P619
}