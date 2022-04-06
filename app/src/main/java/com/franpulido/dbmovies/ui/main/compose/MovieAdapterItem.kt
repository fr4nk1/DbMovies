package com.franpulido.dbmovies.ui.main.compose

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.franpulido.dbmovies.R
import com.franpulido.dbmovies.ui.main.adapter.MoviesAdapter
import com.franpulido.dbmovies.ui.models.MovieModel

const val TITLE_TEST_TAG = "TitleTestTag"
const val VOTE_TEST_TAG = "VoteTestTag"

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MovieAdapterItem(movie: MovieModel, context: Context, listener: (MovieModel) -> Unit) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(
            modifier = Modifier
                .background(
                    Color(
                        ContextCompat.getColor(
                            context,
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
                    painter = rememberImagePainter("${MoviesAdapter.URL_IMAGE}${movie.posterPath}"),
                    contentDescription = movie.title,
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
                        modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 4.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = stringResource(R.string.not_favorite),
                        modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 4.dp)
                    )
                }
                Text(
                    text = movie.title,
                    fontSize = 12.sp,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(4.dp, 0.dp, 4.dp, 4.dp)
                        .testTag(TITLE_TEST_TAG)
                        .weight(1f)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = stringResource(R.string.rating),
                    modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 4.dp)
                )
                Text(
                    text = movie.voteAverage.toString(),
                    fontSize = 14.sp,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(4.dp, 0.dp, 4.dp, 4.dp)
                        .testTag(VOTE_TEST_TAG)
                        .weight(1f)
                )
            }
        }
    }
}