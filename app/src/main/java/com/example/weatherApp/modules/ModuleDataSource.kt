package com.example.weatherApp.modules

import com.example.weatherApp.data.local.LocalDataSource
import com.example.weatherApp.data.local.LocalDataSourceInterface
import com.example.weatherApp.data.remote.RemoteDataSource
import com.example.weatherApp.data.remote.RemoteDataSourceInterface
import com.example.weatherApp.repo.Repository
import com.example.weatherApp.repo.RepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ModuleDataSource {
    @Binds
    fun provideLocalDataSource(localData:LocalDataSource):LocalDataSourceInterface

    @Binds
    fun provideRemoteDataSource(localRemote:RemoteDataSource):RemoteDataSourceInterface

}