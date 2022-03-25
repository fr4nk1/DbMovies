package com.franpulido.database

import com.franpulido.database.models.MovieEntity
import com.franpulido.domain.models.Movie

fun MovieEntity.toDomainMovie(): Movie = Movie(
    id,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    favorite
)

fun Movie.toRoomMovie(): MovieEntity = MovieEntity(
    id,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    favorite
)
