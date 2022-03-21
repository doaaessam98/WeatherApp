package com.example.weatherApp.modules

import android.content.Context
import androidx.room.Room
import com.example.weatherApp.db.CurrentWeatherDataBase
import com.example.weatherApp.db.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CurrentWeatherDataBase =
        Room.databaseBuilder(
            context,
            CurrentWeatherDataBase::class.java,
            "WeatherApp-DB"
        ).build()

    @Provides
    @Singleton
    fun provideCurrentWeatherDao(db: CurrentWeatherDataBase): WeatherDao = db.currentWeatherDao()

}