package com.franpulido.data.source

import com.franpulido.domain.models.Movie

interface RemoteDataSource {
    suspend fun getPopularMovies(apiKey: String): List<Movie>
}