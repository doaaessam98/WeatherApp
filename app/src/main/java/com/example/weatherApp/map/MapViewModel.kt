package com.example.weatherApp.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherApp.favorite.FavouriteModel
import com.example.weatherApp.repo.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(var repository: RepositoryInterface):ViewModel() {

     fun insertFavPlaceInDataBase(favWeather:FavouriteModel) {
        viewModelScope.launch (Dispatchers.IO){
            repository.insertFavouriteCountry(favWeather)
        }

     }


}