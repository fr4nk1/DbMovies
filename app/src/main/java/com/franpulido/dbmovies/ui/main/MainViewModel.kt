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
        object Error : UiModel()
        object Init : UiModel()
    }

    init {
        initScope()
    }

    private fun refresh() {
        _model.value = UiModel.Init
    }

    fun initUi() {
        launch {
            _model.value = UiModel.Loading
            val movies = getPopularMovies.invoke()
            if (movies.isNotEmpty()) _model.value = UiModel.Content(movies)
            else _model.value = UiModel.Error
        }
    }

    fun onMovieClicked(movie: Movie) {
        _model.value = UiModel.Navigation(movie)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}