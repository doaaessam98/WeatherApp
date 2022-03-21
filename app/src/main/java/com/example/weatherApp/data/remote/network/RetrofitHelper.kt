package com.example.weatherApp.data.remote.network

import android.content.ContentValues.TAG
import android.util.Log
import com.example.weatherApp.modules.ApplicationComponent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// api
//https://api.openweathermap.org/data/2.5/onecall
private const val BASE_URL:String="https://api.openweathermap.org/"

@Module
@InstallIn(SingletonComponent::class)
 object RetrofitHelper {

    @Singleton
    @Provides
     public  fun getRetrofitInstance():ApiService{
        Log.e(TAG, "getRetrofitInstance: ", )
         val retrofit=  Retrofit.Builder().baseUrl(BASE_URL).
             addConverterFactory(GsonConverterFactory.create()).build()
       return retrofit.create(ApiService::class.java)
     }
}