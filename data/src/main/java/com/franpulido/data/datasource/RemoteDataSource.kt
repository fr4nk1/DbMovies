package com.franpulido.data.datasource

import com.franpulido.domain.models.Movie

interface RemoteDataSource {
    suspend fun getPopularMovies(apiKey: String): List<Movie>
}