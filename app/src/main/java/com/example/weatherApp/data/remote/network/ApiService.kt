package com.example.weatherApp.data.remote.network

import com.example.weatherApp.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val EXCLUDE="minutely"
interface ApiService {

 @GET("data/2.5/onecall")
 suspend fun getCurrentWeather(
     @Query("lat")lat:Double,
     @Query("lon")lon:Double,
     @Query("units")unit:String,
     @Query("lang")lang:String,
     @Query("exclude")exclude:String,
     @Query("appid")apiId:String= API_KEY
 ):Response<WeatherResponse>
}
