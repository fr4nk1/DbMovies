package com.franpulido.dbmovies.ui.main

import androidx.lifecycle.viewModelScope
import com.franpulido.data.usecases.GetPopularMovies
import com.franpulido.dbmovies.data.models.MovieModel
import com.franpulido.dbmovies.data.models.MoviesModel
import com.franpulido.dbmovies.ui.common.BaseViewModel
import com.franpulido.domain.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getPopularMovies: GetPopularMovies) :
    BaseViewModel<MainViewModel.ViewState, MainViewModel.ViewEvent>() {

    private lateinit var type: TypeOfSort
    private lateinit var movies: List<Movie>

    sealed class ViewEvent {
        class Navigation(val movie: MovieModel) : ViewEvent()
    }

    sealed class ViewState {
        object Loading : ViewState()
        class Content(val movies: MoviesModel) : ViewState()
        object HideIconVote : ViewState()
        object HideIconAlpha : ViewState()
        object ShowIconVote : ViewState()
        object ShowIconAlpha : ViewState()
        object Error : ViewState()
        object Init : ViewState()
    }

    sealed class TypeOfSort {
        object Nothing : TypeOfSort()
        object Alpha : TypeOfSort()
        object Vote : TypeOfSort()
    }

    override suspend fun getInitialViewState(): ViewState = refresh()

    private fun refresh(): ViewState {
        type = TypeOfSort.Nothing
        return ViewState.Init
    }

    fun initUi() {
        viewModelScope.launch {
            updateViewState { ViewState.Loading }

            movies = getPopularMovies.invoke()

            when (type) {
                TypeOfSort.Alpha -> sortMoviesByAlpha()
                TypeOfSort.Vote -> sortMoviesByVote()
            }

            val moviesDataModel = mapList(movies)

            val newList = MoviesModel(moviesDataModel)
            if (moviesDataModel.isNotEmpty()) updateViewState { ViewState.Content(newList) }
            else updateViewState { ViewState.Error }
        }
    }

    fun onMovieClicked(movie: MovieModel) {
        viewModelScope.launch {
            sendViewEvent(ViewEvent.Navigation(movie))
        }
    }

    fun sortByAlpha() {
        type = TypeOfSort.Alpha
        sortMoviesByAlpha()
        val moviesDataModel = mapList(movies)
        val newList = MoviesModel(moviesDataModel)


        updateViewState { ViewState.Content(newList) }
        updateViewState { ViewState.HideIconAlpha }
        updateViewState { ViewState.ShowIconVote }
    }

    fun sortByVote() {
        type = TypeOfSort.Vote
        sortMoviesByVote()
        val moviesDataModel = mapList(movies)
        val newList = MoviesModel(moviesDataModel)

        updateViewState { ViewState.Content(newList) }
        updateViewState { ViewState.HideIconVote }
        updateViewState { ViewState.ShowIconAlpha }
    }

    private fun sortMoviesByAlpha() {
        movies = movies.sortedBy { it.title }
    }

    private fun sortMoviesByVote() {
        movies = movies.sortedByDescending { it.voteAverage }
    }

    private fun mapList(moviesData: List<Movie>) =
        moviesData.map {
            MovieModel(
                it.id,
                it.title,
                it.overview,
                it.releaseDate,
                it.posterPath,
                it.backdropPath,
                it.originalLanguage,
                it.originalTitle,
                it.popularity,
                it.voteAverage,
                it.favorite
            )
        }

}