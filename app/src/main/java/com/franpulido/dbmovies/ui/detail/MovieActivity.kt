package com.franpulido.dbmovies.ui.detail

import android.app.Activity
import android.graphics.drawable.Animatable
import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.franpulido.dbmovies.R
import com.franpulido.dbmovies.databinding.ActivityMovieBinding
import com.franpulido.dbmovies.ui.common.loadUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {

    private val viewModel: MovieViewModel by viewModels()
    private lateinit var binding: ActivityMovieBinding

    companion object {
        const val MOVIE = "MovieActivity:movie"
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.model.observe(this, Observer(::updateUi))

        binding.movieDetailFavorite.setOnClickListener {
            viewModel.onFavoriteClicked()
            setResult(Activity.RESULT_OK)
        }
    }

    override fun onBackPressed() {
        finishAfterTransition()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateUi(model: MovieViewModel.UiModel) = with(binding) {
        val movie = model.movie
        movieToolbar.title = movie.title
        movieToolbar.setTitleTextColor(getColor(R.color.white))
        movieImage.loadUrl("https://image.tmdb.org/t/p/w780${movie.backdropPath}")
        movieSummary.text = movie.overview
        movieTitle.text = movie.originalTitle
        movieDate.text = Html.fromHtml(
            getString(R.string.release_date, movie.releaseDate),
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )
        movieAverage.text = Html.fromHtml(
            getString(R.string.vote_average, movie.voteAverage.toString()),
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )

        if (movie.favorite) {
            val animatedVectorDrawableCompat =
                AnimatedVectorDrawableCompat.create(this@MovieActivity, R.drawable.heart_animation)
            movieDetailFavorite.setImageDrawable(animatedVectorDrawableCompat)
            val animatableIcon = movieDetailFavorite.drawable as Animatable
            animatableIcon.start()
        } else {
            movieDetailFavorite.setImageDrawable(
                ContextCompat.getDrawable(this@MovieActivity, R.drawable.ic_favorite_border)
            )
        }
    }
}