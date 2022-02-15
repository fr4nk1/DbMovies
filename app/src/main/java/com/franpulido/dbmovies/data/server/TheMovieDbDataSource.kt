package com.franpulido.dbmovies.data.server

import com.franpulido.data.source.RemoteDataSource
import com.franpulido.dbmovies.data.toDomainMovie
import com.franpulido.domain.models.Movie

class TheMovieDbDataSource : RemoteDataSource {

    override suspend fun getPopularMovies(apiKey: String): List<Movie> =
        TheMovieDb.service.listPopularMoviesAsync(apiKey).results.map { it.toDomainMovie() }

}