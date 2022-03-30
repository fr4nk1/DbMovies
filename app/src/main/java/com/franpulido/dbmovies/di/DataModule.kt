package com.franpulido.dbmovies.di

import com.franpulido.data.datasource.LocalDataSource
import com.franpulido.data.datasource.RemoteDataSource
import com.franpulido.data.repository.MoviesDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun moviesRepositoryProvider(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        @Named("apiKey") apiKey: String
    ) = MoviesDataRepository(localDataSource, remoteDataSource, apiKey)
}