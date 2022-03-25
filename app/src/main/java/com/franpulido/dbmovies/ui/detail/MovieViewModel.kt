package com.franpulido.dbmovies.ui.detail

import androidx.lifecycle.viewModelScope
import com.franpulido.data.usecases.FindMovieById
import com.franpulido.data.usecases.UpdateMovieFavorite
import com.franpulido.dbmovies.ui.common.BaseViewModel
import com.franpulido.domain.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MovieViewModel @Inject constructor(
    @Named("movieId") private val movieId: Int,
    private val findMovieById: FindMovieById,
    private val updateMovieFavorite: UpdateMovieFavorite
) : BaseViewModel<MovieViewModel.ViewState, MovieViewModel.ViewEvent>() {

    private lateinit var movie: Movie

    sealed class ViewEvent {
        class FavoriteClicked(val movie: Movie) : ViewEvent()
    }

    sealed class ViewState {
        object Loading : ViewState()
        class Content(val movie: Movie) : ViewState()
        object EmptyState : ViewState()
    }

    init {
        findMovie()
    }

    private fun findMovie() = viewModelScope.launch {
        movie = findMovieById.invoke(movieId)
        updateViewState { ViewState.Content(movie) }
    }

    fun onFavoriteClicked() = viewModelScope.launch {
        val movieUpdated = updateMovieFavorite.invoke(movie)
        sendViewEvent(ViewEvent.FavoriteClicked(movieUpdated))
    }

    override suspend fun getInitialViewState(): ViewState = ViewState.EmptyState

}