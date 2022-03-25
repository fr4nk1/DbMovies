package com.franpulido.data.mappers

import com.franpulido.domain.models.Movie
import com.franpulido.network.server.models.MovieDb

fun MovieDb.toDomainMovie(): Movie = Movie(
    0,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath ?: posterPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    false
)

