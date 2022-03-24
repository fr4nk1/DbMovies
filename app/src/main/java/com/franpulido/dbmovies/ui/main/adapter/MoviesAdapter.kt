package com.franpulido.dbmovies.ui.main.adapter

import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.recyclerview.widget.RecyclerView
import com.franpulido.dbmovies.ui.models.MovieModel
import com.franpulido.dbmovies.ui.common.basicDiffUtil
import com.franpulido.dbmovies.ui.main.compose.MovieAdapterItem

class MoviesAdapter(private val listener: (MovieModel) -> Unit) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    var movies: List<MovieModel> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id })

    companion object {
        const val URL_IMAGE = "https://image.tmdb.org/t/p/w185/"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ComposeView(parent.context), listener)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        holder.composeView.disposeComposition()
    }

    class ViewHolder(val composeView: ComposeView, private val listener: (MovieModel) -> Unit) :
        RecyclerView.ViewHolder(composeView) {
        init {
            composeView.setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
        }

        fun bind(movie: MovieModel) {
            composeView.setContent {
                MovieAdapterItem(movie, composeView.context, listener)
            }
        }

    }
}
