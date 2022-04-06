package com.franpulido.dbmovies.ui.detail

import android.app.Activity
import android.graphics.drawable.Animatable
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.franpulido.dbmovies.R
import com.franpulido.dbmovies.databinding.ActivityMovieBinding
import com.franpulido.dbmovies.ui.common.BaseViewModelActivity
import com.franpulido.dbmovies.ui.common.loadUrl
import com.franpulido.domain.models.Movie
import dagger.hilt.android.AndroidEntryPoint


private typealias MovieParent = BaseViewModelActivity<ActivityMovieBinding,
        MovieViewModel.ViewState,
        MovieViewModel.ViewEvent,
        MovieViewModel>

@AndroidEntryPoint
class MovieActivity : MovieParent() {

    companion object {
        const val MOVIE = "MovieActivity:movie"
        const val URL_IMAGE = "https://image.tmdb.org/t/p/w780"
    }

    override val viewModel: MovieViewModel by viewModels()

    override val viewBinding: (LayoutInflater) -> ActivityMovieBinding = {
        ActivityMovieBinding.inflate(it)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun renderViewState(viewState: MovieViewModel.ViewState) {
        binding.progress.visibility =
            if (viewState is MovieViewModel.ViewState.Loading) View.VISIBLE else View.GONE

        when (viewState) {
            is MovieViewModel.ViewState.Content -> updateUi(viewState.movie)
        }
    }

    override fun handleViewEvent(viewEvent: MovieViewModel.ViewEvent) {
        when (viewEvent) {
            is MovieViewModel.ViewEvent.FavoriteClicked -> {
                setFavoriteIcon(viewEvent.movie)
                setResult(Activity.RESULT_OK)
            }
        }
    }

    override fun setupUI() {
        binding.movieDetailFavorite.setOnClickListener {
            viewModel.onFavoriteClicked()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        binding.movieDetailFavorite.requestLayout()
        binding.movieDetailFavorite.visibility = View.GONE
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateUi(movie: Movie) = with(binding) {
        movieToolbar.title = movie.title
        movieToolbar.setTitleTextColor(getColor(R.color.white))
        movieImage.loadUrl("${URL_IMAGE}${movie.backdropPath}")

        with(layoutDescription) {
            movieSummary.text = movie.overview
            movieDate.text = Html.fromHtml(
                getString(R.string.release_date, movie.releaseDate),
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )
            movieAverage.text = Html.fromHtml(
                getString(R.string.vote_average, movie.voteAverage.toString()),
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )
            moviePopularity.text = Html.fromHtml(
                getString(R.string.popularity, movie.popularity.toString()),
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )
        }
        setFavoriteIcon(movie)
    }

    private fun setFavoriteIcon(movie: Movie) {
        if (movie.favorite) {
            val animatedVectorDrawableCompat =
                AnimatedVectorDrawableCompat.create(this@MovieActivity, R.drawable.heart_animation)
            binding.movieDetailFavorite.setImageDrawable(animatedVectorDrawableCompat)
            val animateIcon = binding.movieDetailFavorite.drawable as Animatable
            animateIcon.start()
        } else {
            binding.movieDetailFavorite.setImageDrawable(
                ContextCompat.getDrawable(this@MovieActivity, R.drawable.ic_favorite_border)
            )
        }
    }

}