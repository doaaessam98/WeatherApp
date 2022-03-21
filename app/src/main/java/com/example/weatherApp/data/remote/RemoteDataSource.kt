package com.example.weatherApp.data.remote

import com.example.weatherApp.data.remote.network.ApiService
import com.example.weatherApp.model.WeatherResponse
import org.xml.sax.DTDHandler
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor( private val api: ApiService):RemoteDataSourceInterface {

    override suspend fun getCurrentWeather(
        lat: Double,
        lon:Double,
        units: String,
        lang: String,
        exclude:String
    ): Response<WeatherResponse> =api.
        getCurrentWeather(lat.toDouble(),lon.toDouble(),units,lang,exclude)


}