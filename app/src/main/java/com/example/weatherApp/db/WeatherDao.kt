package com.example.weatherApp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.example.weatherApp.alarm.AlarmModel
import com.example.weatherApp.favorite.FavouriteModel
import com.example.weatherApp.model.WeatherResponse
import java.util.concurrent.Flow

 @Dao
 interface WeatherDao {
    @Query("SELECT * FROM FavouriteWeather")
    fun getAllFavWeather():LiveData<List<FavouriteModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(currentWeatherEntity: WeatherResponse)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavWeather(favWeather:FavouriteModel)
     @Query("SELECT * FROM currentweather")
     fun getAllData(): List<WeatherResponse>
     @Query("SELECT * FROM CurrentWeather WHERE lat=:lat AND lon=:lon ")
     fun getCountryWeather(lat:Double,lon:Double):WeatherResponse
     @Query("SELECT * FROM CurrentWeather WHERE timezone= :timeZone")

     fun getWeatherFromDataBaseByTimeZone(timeZone: String): WeatherResponse

     @Query("DELETE FROM FavouriteWeather WHERE timezone = :timezone")
     fun deleteCurrentWeather(timezone:String)

     @Query("DELETE FROM CurrentWeather WHERE timezone = :timezone")
     fun updateAndAddedToFavorite(timezone:String)

     @Query("Select count(*) from CurrentWeather")
     fun getCount():Int
    ////////alarm
     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertWeaherAlarm(alarm:AlarmModel)

     @Query("SELECT * FROM alarmTable")
     fun getAllAlarm():LiveData<List<AlarmModel>>

     @Query("DELETE FROM alarmTable WHERE alarmId = :id")
     suspend fun deleteAlarm(id : Int)


 }




