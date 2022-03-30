package com.franpulido.data.repository

import com.franpulido.data.datasource.LocalDataSource
import com.franpulido.data.datasource.RemoteDataSource
import com.franpulido.domain.models.Movie
import com.franpulido.domain.repository.MoviesRepository

class MoviesDataRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val apiKey: String
) :  MoviesRepository {

    override suspend fun getPopularMovies(): List<Movie> {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.getPopularMovies(apiKey)
            localDataSource.saveMovies(movies)
        }

        return localDataSource.getPopularMovies()
    }

    override suspend fun findById(id: Int): Movie = localDataSource.findById(id)

    override suspend fun update(movie: Movie) = localDataSource.update(movie)
}