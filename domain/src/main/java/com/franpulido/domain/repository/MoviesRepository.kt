package com.franpulido.domain.repository

import com.franpulido.domain.models.Movie

interface MoviesRepository {
    suspend fun getPopularMovies(): List<Movie>
    suspend fun findById(id: Int): Movie
    suspend fun update(movie: Movie)
}