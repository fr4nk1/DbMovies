package com.franpulido.domain.usecases

import com.franpulido.domain.models.Movie
import com.franpulido.domain.repository.MoviesRepository

class FindMovieById(private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke(id: Int): Movie = moviesRepository.findById(id)
}

class GetPopularMovies(private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke(): List<Movie> = try{
        moviesRepository.getPopularMovies()
    }catch (e: Exception){
        emptyList()
    }
}

class UpdateMovieFavorite(private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke(movie: Movie): Movie = with(movie) {
        copy(favorite = !favorite).also { moviesRepository.update(it) }
    }
}