package com.franpulido.dbmovies.ui.story


import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.franpulido.dbmovies.R
import com.franpulido.dbmovies.data.models.MovieModel
import com.franpulido.dbmovies.ui.common.loadUrl
import com.franpulido.dbmovies.ui.common.startActivity
import com.franpulido.dbmovies.ui.detail.MovieActivity
import kotlinx.android.synthetic.main.layout_story_view.*

class StoryViewFragment : Fragment(R.layout.fragment_story_view) {
    private var movie: MovieModel? = null

    companion object {
        fun newInstance(storiesDataModel: MovieModel) = StoryViewFragment()
            .apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_STORY_DATA, storiesDataModel)
                }
            }

        const val KEY_STORY_DATA = "KEY_STORY_DATA"
        const val URL_IMAGE = "https://image.tmdb.org/t/p/w780"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movie = arguments?.getParcelable(KEY_STORY_DATA)
        setData()
    }

    private fun setData() {
        textTitle?.text = movie?.title
        textTitleDescription?.text = movie?.overview
        textVote?.text = movie?.voteAverage.toString()

        imageBg?.loadUrl("${URL_IMAGE}${movie?.posterPath}")

        if(movie!!.favorite){
            imageFavorite.setImageDrawable(context?.let { ContextCompat.getDrawable(it, R.drawable.ic_favorite) })
        }else{
            imageFavorite.setImageDrawable(context?.let { ContextCompat.getDrawable(it, R.drawable.ic_favorite_border) })
        }

        clRoot.setOnClickListener {
            activity?.startActivity<MovieActivity> {
                putExtra(MovieActivity.MOVIE, movie?.id)
            }
        }

    }

}