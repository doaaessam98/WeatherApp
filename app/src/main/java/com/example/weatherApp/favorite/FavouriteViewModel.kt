package com.example.weatherApp.favorite

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherApp.model.WeatherResponse
import com.example.weatherApp.repo.Repository
import com.example.weatherApp.repo.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
   var  repository: RepositoryInterface)
    :ViewModel() {
    fun getAllFavouriteFromDataBase():LiveData<List<FavouriteModel>>{

             return repository.getFavourite()
        }
     fun deleteFromFav(timezone:String){
         Log.e(TAG, "deleteFromFav: ", )
         viewModelScope.launch(Dispatchers.IO) {
             repository.deleteWeatherFromFavourite(timezone)
         }
     }



}