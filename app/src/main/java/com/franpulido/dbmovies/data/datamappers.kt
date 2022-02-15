package com.franpulido.dbmovies.data

import com.franpulido.dbmovies.data.database.MovieEntity
import com.franpulido.dbmovies.data.server.MovieDb
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
