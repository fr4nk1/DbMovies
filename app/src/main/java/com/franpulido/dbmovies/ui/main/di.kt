package com.franpulido.dbmovies.ui.main

import com.franpulido.data.repository.MoviesDataRepository
import com.franpulido.domain.usecases.GetPopularMovies
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class MainActivityModule {

    @Provides
    @ViewModelScoped
    fun getPopularMoviesProvider(moviesRepository: MoviesDataRepository) =
        GetPopularMovies(moviesRepository)
}