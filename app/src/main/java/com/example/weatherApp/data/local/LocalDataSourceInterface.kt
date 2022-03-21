package com.example.weatherApp.data.local

import androidx.lifecycle.LiveData
import com.example.weatherApp.alarm.AlarmModel
import com.example.weatherApp.favorite.FavouriteModel
import com.example.weatherApp.model.WeatherResponse
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow


interface LocalDataSourceInterface {
        fun getWeatherFromDataBase(lat:Double, lon:Double): WeatherResponse
       suspend fun insertResponseWeather(weatherResponse:WeatherResponse)
       fun deleteWeatherFromFavourite(timeZone: String)
       fun getAllFavourite():LiveData<List<FavouriteModel>>
       fun addToFavourite()
       suspend fun insertFavWeather(favWeather:FavouriteModel)

        fun getAllWeatherAlarm():LiveData<List<AlarmModel>>
        suspend fun insertWeatherAlarm(weatherAlarm:AlarmModel)
        suspend fun deleteAlarm(id:Int)
    fun getWeatherFromDataBaseByTimeZone(timeZone: String): WeatherResponse
    fun getAllData(): List<WeatherResponse>

}