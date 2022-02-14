package com.franpulido.dbmovies.ui.main

import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.franpulido.dbmovies.R
import com.franpulido.dbmovies.ui.common.basicDiffUtil
import com.franpulido.domain.models.Movie

class MoviesAdapter(
    private val listener: (Movie) -> Unit
) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    var movies: List<Movie> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

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

    class ViewHolder(
        val composeView: ComposeView,
        private val listener: (Movie) -> Unit
    ) :
        RecyclerView.ViewHolder(composeView) {
        init {
            composeView.setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
        }

        fun bind(movie: Movie) {
            composeView.setContent {
                paintItem(movie)
            }
        }

        @OptIn(ExperimentalCoilApi::class)
        @Composable
        fun paintItem(movie: Movie) {
            Card(modifier = Modifier.padding(8.dp)) {
                Column(
                    modifier = Modifier
                        .background(
                            Color(
                                ContextCompat.getColor(
                                    composeView.context,
                                    R.color.pink_200
                                )
                            )
                        )
                        .clickable { listener(movie) },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Box {
                        Image(
                            painter = rememberImagePainter("https://image.tmdb.org/t/p/w185/${movie.posterPath}"),
                            contentDescription = "${movie.title}",
                            modifier = Modifier
                                .aspectRatio(0.7f)
                                .padding(8.dp)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (movie.favorite) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = stringResource(R.string.favorite),
                                modifier = Modifier.padding(8.dp,0.dp,0.dp,4.dp)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = stringResource(R.string.not_favorite),
                                modifier = Modifier.padding(8.dp,0.dp,0.dp,4.dp)
                            )
                        }
                        Text(
                            text = movie.title,
                            fontSize = 12.sp,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(4.dp, 0.dp, 4.dp,4.dp).weight(1f)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = stringResource(R.string.rating),
                            modifier = Modifier.padding(8.dp,0.dp,0.dp,4.dp)
                        )
                        Text(
                            text = movie.voteAverage.toString(),
                            fontSize = 14.sp,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(4.dp, 0.dp, 4.dp,4.dp).weight(1f)
                        )
                    }
                }
            }
        }
    }
}