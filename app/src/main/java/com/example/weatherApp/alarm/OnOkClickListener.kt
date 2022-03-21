package com.example.weatherApp.alarm

import android.view.View
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


interface OnDeleteAlarmClickListener {

    fun onDeleteAlarmClicked(view:View,id:Int)
}