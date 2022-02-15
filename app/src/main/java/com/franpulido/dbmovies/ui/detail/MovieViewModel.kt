package com.franpulido.dbmovies.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.franpulido.dbmovies.ui.common.ScopedViewModel
import com.franpulido.domain.models.Movie
import com.franpulido.usecases.FindMovieById
import com.franpulido.usecases.UpdateMovieFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MovieViewModel @Inject constructor(
    @Named("movieId") private val movieId: Int,
    private val findMovieById: FindMovieById,
    private val updateMovieFavorite: UpdateMovieFavorite
) :
    ScopedViewModel() {

    class UiModel(val movie: Movie)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) findMovie()
            return _model
        }

    private fun findMovie() = launch {
        _model.value = UiModel(findMovieById.invoke(movieId))
    }

    fun onFavoriteClicked() = launch {
        _model.value?.movie?.let {
            _model.value = UiModel(updateMovieFavorite.invoke(it))
        }
    }
}