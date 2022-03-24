package com.franpulido.dbmovies.di

import android.app.Application
import androidx.room.Room
import com.franpulido.data.source.LocalDataSource
import com.franpulido.data.source.RemoteDataSource
import com.franpulido.dbmovies.R
import com.franpulido.dbmovies.data.database.MovieDatabase
import com.franpulido.dbmovies.data.database.RoomDataSource
import com.franpulido.dbmovies.data.source.TheMovieDbDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyProvider(app: Application): String = app.getString(R.string.api_key)

    @Provides
    @Singleton
    fun databaseProvider(app: Application) =
        Room.databaseBuilder(app, MovieDatabase::class.java, "movieDb").build()

    @Provides
    fun localDataSourceProvider(db: MovieDatabase): LocalDataSource = RoomDataSource(db)

    @Provides
    fun remoteDataSourceProvider(): RemoteDataSource = TheMovieDbDataSource()

}