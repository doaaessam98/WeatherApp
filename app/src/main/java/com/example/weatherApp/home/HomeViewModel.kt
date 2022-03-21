package com.example.weatherApp.home

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherApp.model.WeatherResponse
import com.example.weatherApp.repo.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RepositoryInterface,@ApplicationContext var context: Context )
    : ViewModel() {
    lateinit var job: Job
    lateinit var  sharedPref:SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    init {
        sharedPref = context.getSharedPreferences(SHARD_NAME, Context.MODE_PRIVATE)

    }
   private var _getWeather = MutableLiveData<WeatherResponse>()
    var getWeather:LiveData<WeatherResponse> =_getWeather


    fun getDataFromDataBase(
        lat: Double, lon: Double
    ){


        job = viewModelScope.launch(Dispatchers.IO) {
            repository.getWeatherDataFromApiAndInserInDataBase(lat, lon)
        }

        viewModelScope.launch(Dispatchers.IO) {
            job.join()
            var timeZone:String?=sharedPref.getString("currentzone","")
            var res1= repository.getWeatherFromDataBaseByTimeZone(timeZone!!)

            _getWeather.postValue(res1)


        }



    }


}

