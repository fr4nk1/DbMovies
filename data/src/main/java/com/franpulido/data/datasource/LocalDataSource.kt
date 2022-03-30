package com.franpulido.data.datasource

import com.franpulido.domain.models.Movie

interface LocalDataSource {
    suspend fun isEmpty(): Boolean
    suspend fun saveMovies(movies: List<Movie>)
    suspend fun getPopularMovies(): List<Movie>
    suspend fun findById(id: Int): Movie
    suspend fun update(movie: Movie)
}