package com.franpulido.dbmovies.ui.main.fragment

import androidx.lifecycle.viewModelScope
import com.franpulido.data.usecases.GetPopularMoviesStory
import com.franpulido.dbmovies.ui.common.BaseViewModel
import com.franpulido.dbmovies.ui.models.MovieModel
import com.franpulido.dbmovies.ui.models.MoviesModel
import com.franpulido.domain.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getPopularMoviesStory: GetPopularMoviesStory) :
    BaseViewModel<HomeViewModel.ViewState, HomeViewModel.ViewEvent>() {

    private lateinit var type: TypeOfSort
    private lateinit var movies: List<Movie>

    object ViewEvent

    sealed class ViewState {
        object Loading : ViewState()
        class Content(val movies: MoviesModel) : ViewState()
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

            movies = getPopularMoviesStory.invoke()

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