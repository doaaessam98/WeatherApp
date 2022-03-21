package com.example.weatherApp.model


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName ="CurrentWeather")
data class WeatherResponse(
    @PrimaryKey
    @SerializedName("timezone")
    val timezone: String,
    var isFav:Boolean,
    @SerializedName("timezone_offset")
    val timezoneOffset: Int,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,

    @Embedded
    @SerializedName("current")
    val current: Current,
    @SerializedName("daily")
    val daily: List<Daily>,
    @SerializedName("hourly")
    val hourly: List<Hourly>,
//    @SerializedName("minutely")
//    val minutely: List<Minutely>

)