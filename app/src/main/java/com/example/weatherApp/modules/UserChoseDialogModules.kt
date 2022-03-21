package com.example.weatherApp.modules

import com.example.weatherApp.dilog.OnOkClickListener
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


//@Module
//@InstallIn(SingletonComponent::class)
interface UserChoseDialogModules {

//    @Binds
    fun provideOkClickListener():OnOkClickListener


}