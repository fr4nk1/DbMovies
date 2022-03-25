package com.franpulido.data.repository

import com.franpulido.data.source.LocalDataSource
import com.franpulido.data.source.RemoteDataSource
import com.franpulido.domain.models.Movie

class MoviesRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val apiKey: String
) {

    suspend fun getPopularMovies(): List<Movie> {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.getPopularMovies(apiKey)
            localDataSource.saveMovies(movies)
        }

        return localDataSource.getPopularMovies()
    }

    suspend fun getPopularMoviesStory(): List<Movie> {
        return if (localDataSource.isEmpty()) {
            remoteDataSource.getPopularMovies(apiKey)
        } else localDataSource.getPopularMovies()
    }

    suspend fun findById(id: Int): Movie = localDataSource.findById(id)

    suspend fun update(movie: Movie) = localDataSource.update(movie)
}