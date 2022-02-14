package com.franpulido.dbmovies.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.franpulido.data.repository.MoviesRepository
import com.franpulido.usecases.FindMovieById
import com.franpulido.usecases.ToggleMovieFavorite
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import java.lang.IllegalStateException
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class MovieActivityModule {

    @Provides
    fun findMovieByIdProvider(moviesRepository: MoviesRepository) = FindMovieById(moviesRepository)

    @Provides
    fun toggleMovieFavoriteProvider(moviesRepository: MoviesRepository) =
        ToggleMovieFavorite(moviesRepository)

    @Provides
    @Named("movieId")
    fun movieIdProvider(stateHandle: SavedStateHandle): Int =
        stateHandle.get<Int>(MovieActivity.MOVIE)
            ?: throw IllegalStateException("Error!! Movie Id Not Found :(")
}