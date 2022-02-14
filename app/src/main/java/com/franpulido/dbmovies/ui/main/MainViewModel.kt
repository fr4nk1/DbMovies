package com.franpulido.dbmovies.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        class Content(val movies: List<Movie>) : UiModel()
        class Navigation(val movie: Movie) : UiModel()
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

    private fun refresh() {
        _model.value = UiModel.Init
        type = TypeOfSort.Nothing
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    fun initUi() {
        launch {
            _model.value = UiModel.Loading
            movies = getPopularMovies.invoke()
            when(type){
                TypeOfSort.Alpha -> sortMoviesByAlpha()
                TypeOfSort.Vote -> sortMoviesByVote()
            }
            if (movies.isNotEmpty()) _model.value = UiModel.Content(movies)
            else _model.value = UiModel.Error
        }
    }

    fun onMovieClicked(movie: Movie) {
        _model.value = UiModel.Navigation(movie)
    }

    fun sortByAlpha() {
        type = TypeOfSort.Alpha
        sortMoviesByAlpha()
        _model.value = UiModel.Content(movies)
        _model.value = UiModel.HideIconAlpha
        _model.value = UiModel.ShowIconVote
    }

    fun sortByVote() {
        type = TypeOfSort.Vote
        movies = movies.sortedByDescending { it.voteAverage }
        _model.value = UiModel.Content(movies)
        _model.value = UiModel.HideIconVote
        _model.value = UiModel.ShowIconAlpha
    }

    private fun sortMoviesByAlpha() {
        movies = movies.sortedBy { it.title }
    }

    private fun sortMoviesByVote() {
        movies = movies.sortedByDescending { it.voteAverage }
    }
}