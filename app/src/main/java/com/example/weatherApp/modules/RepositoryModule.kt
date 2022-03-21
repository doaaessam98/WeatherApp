package com.example.weatherApp.modules

import com.example.weatherApp.data.local.LocalDataSourceInterface
import com.example.weatherApp.data.remote.RemoteDataSourceInterface
import com.example.weatherApp.repo.Repository
import com.example.weatherApp.repo.RepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
 interface RepositoryModule {

    @Binds
    fun provideMainRepository(
    repository: Repository
    ): RepositoryInterface


}