package com.franpulido.dbmovies.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.franpulido.dbmovies.data.models.MovieModel
import com.franpulido.dbmovies.data.models.MoviesModel
import com.franpulido.dbmovies.ui.common.ScopedViewModel
import com.franpulido.domain.models.Movie
import com.franpulido.usecases.GetPopularMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getPopularMovies: GetPopularMovies) :
    ScopedViewModel() {

    private lateinit var type: TypeOfSort
    private lateinit var movies: List<Movie>
    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val movies: MoviesModel) : UiModel()
        class Navigation(val movie: MovieModel) : UiModel()
        object HideIconVote : MainViewModel.UiModel()
        object HideIconAlpha : MainViewModel.UiModel()
        object ShowIconVote : MainViewModel.UiModel()
        object ShowIconAlpha : MainViewModel.UiModel()
        object Error : UiModel()
        object Init : UiModel()
    }

    sealed class TypeOfSort {
        object Nothing : TypeOfSort()
        object Alpha : TypeOfSort()
        object Vote : TypeOfSort()
    }

    init {
        initScope()
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    private fun refresh() {
        _model.postValue(UiModel.Init)
        type = TypeOfSort.Nothing
    }

    private fun sortMoviesByAlpha() {
        movies = movies.sortedBy { it.title }
    }

    private fun sortMoviesByVote() {
        movies = movies.sortedByDescending { it.voteAverage }
    }

    fun initUi() {
        launch {
            _model.postValue(UiModel.Loading)
            movies = getPopularMovies.invoke()
            val moviesDataModel = mapList(movies)

            when (type) {
                TypeOfSort.Alpha -> sortMoviesByAlpha()
                TypeOfSort.Vote -> sortMoviesByVote()
            }

            val newList = MoviesModel(moviesDataModel)
            if (moviesDataModel.isNotEmpty()) _model.postValue(UiModel.Content(newList))
            else _model.postValue(UiModel.Error)
        }
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

    fun onMovieClicked(movie: MovieModel) {
        _model.value = UiModel.Navigation(movie)
    }

    fun sortByAlpha() {
        type = TypeOfSort.Alpha
        sortMoviesByAlpha()
        val moviesDataModel = mapList(movies)
        val newList = MoviesModel(moviesDataModel)
        _model.value = movies?.let { UiModel.Content(newList) }
        _model.value = UiModel.HideIconAlpha
        _model.value = UiModel.ShowIconVote
    }

    fun sortByVote() {
        type = TypeOfSort.Vote
        sortMoviesByVote()
        val moviesDataModel = mapList(movies)
        val newList = MoviesModel(moviesDataModel)
        _model.value = movies?.let { UiModel.Content(newList) }
        _model.value = UiModel.HideIconVote
        _model.value = UiModel.ShowIconAlpha
    }

}