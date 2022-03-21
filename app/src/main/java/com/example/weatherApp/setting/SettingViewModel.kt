package com.example.weatherApp.setting

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherApp.repo.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel  @Inject constructor(var repository: RepositoryInterface):ViewModel() {


//    var isArabic = ObservableBoolean(false)
//    var isEnglish= ObservableBoolean(false)
//    var isGps = ObservableBoolean(false)
//    var isMap= ObservableBoolean(false)
//    var iscelsius = ObservableBoolean(false)
//    var isKelvin= ObservableBoolean(false)
//    var isfahrenheit = ObservableBoolean(false)
//    var ismeter= ObservableBoolean(false)
//    var ismile = ObservableBoolean(false)



    fun refreshData(){

        viewModelScope.launch (Dispatchers.IO ){
            repository.refreshDataBase()

        }
    }
}