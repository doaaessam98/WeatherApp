package com.example.weatherApp.repo

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.example.weatherApp.alarm.AlarmModel
import com.example.weatherApp.data.local.LocalDataSourceInterface
import com.example.weatherApp.data.remote.RemoteDataSourceInterface
import com.example.weatherApp.favorite.FavouriteModel
import com.example.weatherApp.home.SHARD_NAME
import com.example.weatherApp.model.WeatherResponse
import com.example.weatherApp.utils.Constants
import com.example.weatherApp.utils.Constants.LANG_UNIT
import com.example.weatherApp.utils.Constants.WIND_UNIT
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import retrofit2.Response

import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.log

@Singleton
class Repository @Inject constructor(
    val localDataSource: LocalDataSourceInterface
    ,val remoteDataSource: RemoteDataSourceInterface,@ApplicationContext var context: Context)
    :RepositoryInterface {
    lateinit var  sharedPref:SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var lang: String
    lateinit var units: String
    lateinit var  re:Response<WeatherResponse>
    private var jop: Job? = null
   private fun init (){
       sharedPref = context.getSharedPreferences(SHARD_NAME, Context.MODE_PRIVATE)
       editor= sharedPref.edit()
        lang = sharedPref.getString(LANG_UNIT, "en").toString()
        units = sharedPref.getString(WIND_UNIT, "metric").toString()
    }

    override suspend fun getWeatherDataFromApiAndInserInDataBase(
         lat: Double, lon: Double){
        if(isOnline(context)) {
            init()
          var response = remoteDataSource.getCurrentWeather(lat, lon, units,lang,"minutely")

             if (response.isSuccessful) {
                 editor.putString("currentzone",response.body()?.timezone)
                 editor.commit()
                 response.body()?.let {
                     localDataSource.insertResponseWeather(it)

                 }
             } else {
                 Log.e(TAG, "Error"+response.errorBody() )
             }
         }

   else{

      Toast.makeText(context," network connection failed",Toast.LENGTH_LONG).show()
   }


    }
    override  fun getWeatherFromDataBase(lat: Double, lon: Double, lang:String, unit:String)
            :WeatherResponse {
        return localDataSource.getWeatherFromDataBase(lat,lon)
    }
    override  fun getWeatherFromDataBaseByTimeZone(timeZone:String)
            :WeatherResponse {

        return localDataSource.getWeatherFromDataBaseByTimeZone(timeZone)
    }

    override suspend fun insertFavouriteCountry(favWeather: FavouriteModel) {
        localDataSource.insertFavWeather(favWeather)
    }



    ///// alarm
    override  fun getAllWeatherAlarms(): LiveData<List<AlarmModel>> {
       return localDataSource.getAllWeatherAlarm()
    }

    override suspend fun insertWeatherAlarm(weatherAlarm: AlarmModel) {

        localDataSource.insertWeatherAlarm(weatherAlarm)
    }

    override suspend fun deleteAlarm(id: Int) {
        localDataSource.deleteAlarm(id)
    }

    override suspend fun insertResponseWeather(weatherResponse: WeatherResponse) {
       localDataSource.insertResponseWeather(weatherResponse)
    }

    override fun deleteWeatherFromFavourite(timeZone: String) {
        localDataSource.deleteWeatherFromFavourite(timeZone)
    }

    override fun getFavourite(): LiveData<List<FavouriteModel>> {
        return  localDataSource.getAllFavourite()
    }

    override fun addToFavourite() {
        localDataSource.addToFavourite()
    }



    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }
        }
        return false
    }
    override suspend fun refreshDataBase() {

            var list = localDataSource.getAllData()
            var favList=localDataSource.getAllFavourite()
            for (item in list!!) {
                getWeatherDataFromApiAndInserInDataBase(item.lat, item.lon)
             }

        }

    override  fun getDataForAlarm(lat: Double, lon: Double, timeZone: String):WeatherResponse {
        if(isOnline(context)){
            CoroutineScope(Dispatchers.IO).launch{
                getWeatherDataFromApiAndInserInDataBase(lat,lon)
                println("gggg")
           }
        }

        return  getWeatherFromDataBaseByTimeZone(timeZone)

    }
}
