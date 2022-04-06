package com.franpulido.dbmovies.ui.main.story

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.franpulido.dbmovies.R
import com.franpulido.dbmovies.ui.common.loadUrl
import com.franpulido.dbmovies.ui.detail.MovieActivity
import com.franpulido.dbmovies.ui.main.listener.ListenerResult
import com.franpulido.dbmovies.ui.models.MovieModel
import kotlinx.android.synthetic.main.layout_story_view.*

class StoryViewFragment : Fragment(R.layout.fragment_story_view) {
    private var movie: MovieModel? = null
    private lateinit var listener : ListenerResult

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

    fun setListener(listener: ListenerResult) {
        this.listener = listener
    }

    private fun setData() {
        textTitle?.text = movie?.title
        textTitleDescription?.text = movie?.overview
        textVote?.text = movie?.voteAverage.toString()

        imageBg?.loadUrl("${URL_IMAGE}${movie?.posterPath}")

        clRoot.setOnClickListener {
            val intent = Intent(activity, MovieActivity::class.java)
            intent.putExtra(MovieActivity.MOVIE, movie?.id)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity as FragmentActivity)
            launchDetailActivity.launch(intent, options)
        }
    }

    private var launchDetailActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                listener.actionResult()
            }
        }

}