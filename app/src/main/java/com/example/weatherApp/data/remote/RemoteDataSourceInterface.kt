package com.example.weatherApp.data.remote

import com.example.weatherApp.model.WeatherResponse
import retrofit2.Response

interface RemoteDataSourceInterface {


  suspend  fun getCurrentWeather(lat: Double, lon: Double,
                                units: String, lang:String,exclude:String):Response<WeatherResponse>
}
