package com.example.weatherApp.dilog

import android.view.View
import android.widget.RadioGroup
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel



class UserDialogViewModel : ViewModel() {

    var isMap =ObservableBoolean(false)
    var isGps= ObservableBoolean(false)

//   fun map(){
////    isGps.set(true)
////       println("doaa")
//
//   }


}
