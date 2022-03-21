package com.example.weatherApp.db

import androidx.room.TypeConverter
import com.example.weatherApp.model.Daily
import com.example.weatherApp.model.Hourly
import com.example.weatherApp.model.Minutely
import com.example.weatherApp.model.Weather
import com.google.gson.Gson

class DataConverter {


    @TypeConverter
    fun  listHourlyToJson (value:List<Hourly>): String = Gson().toJson(value)
    @TypeConverter
    fun  listDailyToJson (value:List<Daily>) = Gson().toJson(value)
//    @TypeConverter
//    fun  listAlertToJson (value:List<Alerts>?) = Gson().toJson(value)
//     @TypeConverter
//     fun  listMinutelyToJson (value:List<Minutely>): String = Gson().toJson(value)
    @TypeConverter
    fun  listWeatherToJson (value: List<Weather>) = Gson().toJson(value)
    @TypeConverter
    fun jsonToWeatherList(value: String) = Gson().fromJson(value, Array<Weather>::class.java).toList()
    @TypeConverter
    fun jsonToHourlyList(value: String) = Gson().fromJson(value, Array<Hourly>::class.java).toList()
//    @TypeConverter
//    fun jsonToMinutelyList(value: String) = Gson().fromJson(value, Array<Minutely>::class.java).toList()
    @TypeConverter
    fun jsonToDailyList(value: String) = Gson().fromJson(value, Array<Daily>::class.java).toList()

//    @TypeConverter
//    fun jsonToAlertList(value: String?): List<Alerts>? {
//        value?.let {
//            return Gson().fromJson(value, Array<Alerts>::class.java)?.toList()
//        }
//        return emptyList()
//    }
}