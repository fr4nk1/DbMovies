package com.franpulido.dbmovies.data.source

import com.franpulido.data.source.RemoteDataSource
import com.franpulido.dbmovies.data.toDomainMovie
import com.franpulido.domain.models.Movie
import com.franpulido.network.server.client.TheMovieDb

class TheMovieDbDataSource : RemoteDataSource {

    override suspend fun getPopularMovies(apiKey: String): List<Movie> =
        TheMovieDb.service.listPopularMoviesAsync(apiKey).results.map { it.toDomainMovie() }

}