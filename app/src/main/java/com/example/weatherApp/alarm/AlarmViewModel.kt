package com.example.weatherApp.alarm

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherApp.model.WeatherResponse
import com.example.weatherApp.repo.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class AlarmViewModel  @Inject constructor(
  var   repository: RepositoryInterface)
    :ViewModel() {

    private var _getWeatherForAlarm = MutableLiveData<WeatherResponse>()

    var dataFormAlarm:LiveData<WeatherResponse> =_getWeatherForAlarm
    private var _getWeatherAlarm = MutableLiveData<List<AlarmModel>>()
    var getWeatherAlarm: LiveData<List<AlarmModel>> =_getWeatherAlarm

     fun getAllWeatherAlarm():LiveData<List<AlarmModel>>{

         return repository.getAllWeatherAlarms()



     }

    fun deleteALarm(id: Int) {
        viewModelScope.launch (Dispatchers.IO){
          repository.deleteAlarm(id)
            }
        }

    fun getDateFromApiForAlarm(lat: Double, lon: Double,timeZone:String){
        viewModelScope.launch (Dispatchers.IO){
            var result=repository.getDataForAlarm(lat,lon,timeZone)
            _getWeatherForAlarm.postValue(result)
        }
    }


}


