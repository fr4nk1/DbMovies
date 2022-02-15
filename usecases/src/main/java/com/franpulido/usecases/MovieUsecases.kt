package com.franpulido.usecases

import com.franpulido.data.repository.MoviesRepository
import com.franpulido.domain.models.Movie

class FindMovieById(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(id: Int): Movie = moviesRepository.findById(id)
}

class GetPopularMovies(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(): List<Movie> = try{
        moviesRepository.getPopularMovies()
    }catch (e: Exception){
        emptyList()
    }
}

class UpdateMovieFavorite(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(movie: Movie): Movie = with(movie) {
        copy(favorite = !favorite).also { moviesRepository.update(it) }
    }
}