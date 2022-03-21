package com.example.weatherApp.data.local

import androidx.lifecycle.LiveData
import com.example.weatherApp.alarm.AlarmModel
import com.example.weatherApp.db.WeatherDao
import com.example.weatherApp.favorite.FavouriteModel
import com.example.weatherApp.model.WeatherResponse
import javax.inject.Inject

class LocalDataSource @Inject constructor( private val currentWeatherDao: WeatherDao) :LocalDataSourceInterface{



    override  fun getWeatherFromDataBase(lat:Double, lon:Double): WeatherResponse {
     // Log.e(TAG, "getWeatherFromDataBase: local source"+currentWeatherDao.getCountryWeather(lat,lon).current )
        return currentWeatherDao.getCountryWeather(lat,lon)
    }

    override fun getWeatherFromDataBaseByTimeZone(timeZone: String): WeatherResponse {
        return currentWeatherDao.getWeatherFromDataBaseByTimeZone(timeZone)
    }

    override suspend fun insertResponseWeather(weatherResponse: WeatherResponse) {
           currentWeatherDao.insertCurrentWeather(weatherResponse)
    }

    override fun getAllData(): List<WeatherResponse> {
        return currentWeatherDao.getAllData()
    }

    ///////////////////////
    override fun deleteWeatherFromFavourite(timeZone: String) {
        currentWeatherDao.deleteCurrentWeather(timeZone)

    }

    override fun getAllFavourite():LiveData<List<FavouriteModel>> {
       return currentWeatherDao.getAllFavWeather()
    }

    override fun addToFavourite() {

    }

    override  suspend fun insertFavWeather(favWeather: FavouriteModel) {
        currentWeatherDao.insertFavWeather(favWeather)
    }

//////////////////////////
    override  fun getAllWeatherAlarm():LiveData<List<AlarmModel>> {
      return  currentWeatherDao.getAllAlarm()
    }

    override suspend fun insertWeatherAlarm(weatherAlarm: AlarmModel) {
        currentWeatherDao.insertWeaherAlarm(weatherAlarm)
    }

    override suspend fun deleteAlarm(id: Int) {
        currentWeatherDao.deleteAlarm(id)
    }
}