package com.example.weatherApp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherApp.alarm.AlarmModel
import com.example.weatherApp.favorite.FavouriteModel
import com.example.weatherApp.model.WeatherResponse

@Database(
    entities = arrayOf(WeatherResponse::class, FavouriteModel::class, AlarmModel::class),
    version = 3,
    exportSchema = false
)
@TypeConverters(DataConverter::class)
abstract class CurrentWeatherDataBase : RoomDatabase() {
    abstract fun currentWeatherDao():WeatherDao


}