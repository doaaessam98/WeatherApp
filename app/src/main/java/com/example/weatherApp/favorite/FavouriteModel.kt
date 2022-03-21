package com.example.weatherApp.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="FavouriteWeather")
data class FavouriteModel(
    @PrimaryKey
    val timeZone: String,
    var  lat:Double,
    var  lon:Double
)