package com.example.weatherApp.dilog

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

//@Module
//@InstallIn(SingletonComponent::class)
interface OnOkClickListener {

    fun onButtonOkClicked(type:String)
}