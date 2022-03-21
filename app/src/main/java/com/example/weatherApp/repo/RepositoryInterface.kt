package com.example.weatherApp.repo

import android.content.Context
import android.widget.ListView
import androidx.lifecycle.LiveData
import com.example.weatherApp.alarm.AlarmModel
import com.example.weatherApp.favorite.FavouriteModel
import com.example.weatherApp.model.WeatherResponse
import retrofit2.Response


interface RepositoryInterface {

//    fun getDataFromDataBase()
  suspend fun getWeatherDataFromApiAndInserInDataBase(
   lat: Double, lon: Double)

   fun getWeatherFromDataBase(lat:Double, lon:Double, lang:String, unit:String):WeatherResponse
   suspend fun insertResponseWeather(weatherResponse:WeatherResponse)
  fun deleteWeatherFromFavourite(timeZone: String)
  fun getFavourite():LiveData<List<FavouriteModel>>
  fun addToFavourite()
  suspend fun insertFavouriteCountry(favWeather:FavouriteModel)
     fun getAllWeatherAlarms():LiveData<List<AlarmModel>>
    suspend fun insertWeatherAlarm(weatherAlarm:AlarmModel)
  suspend fun deleteAlarm(id:Int)
 fun getWeatherFromDataBaseByTimeZone(timeZone:String):WeatherResponse
  suspend fun   refreshDataBase()

   fun getDataForAlarm(lat:Double, lon:Double, timeZone:String):WeatherResponse


}