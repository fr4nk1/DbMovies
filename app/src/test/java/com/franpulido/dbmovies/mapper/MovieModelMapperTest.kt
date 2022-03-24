package com.franpulido.dbmovies.mapper

import com.franpulido.data.toDomainMovie
import com.franpulido.data.toRoomMovie
import com.franpulido.dbmovies.mocks.MovieMockProvider
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieModelMapperTest {

    @Test
    fun testMovieEntityToMovie() {
        val movieDummy = MovieMockProvider.getDummyMovieEntity()
        val movie = movieDummy.toDomainMovie()

        assertEquals(movieDummy.id, movie.id)
        assertEquals(movieDummy.title, movie.title)
        assertEquals(movieDummy.overview, movie.overview)
        assertEquals(movieDummy.releaseDate, movie.releaseDate)
        assertEquals(movieDummy.posterPath, movie.posterPath)
        assertEquals(movieDummy.backdropPath, movie.backdropPath)
        assertEquals(movieDummy.originalLanguage, movie.originalLanguage)
        assertEquals(movieDummy.originalTitle, movie.originalTitle)
        assertEquals(movieDummy.popularity.toString(), movie.popularity.toString())
        assertEquals(movieDummy.voteAverage.toString(), movie.voteAverage.toString())
        assertEquals(movieDummy.favorite, movie.favorite)
    }

    @Test
    fun testMovieToMovieEntity() {
        val movieDummy = MovieMockProvider.getDummyMovie()
        val movie = movieDummy.toRoomMovie()

        assertEquals(movieDummy.id, movie.id)
        assertEquals(movieDummy.title, movie.title)
        assertEquals(movieDummy.overview, movie.overview)
        assertEquals(movieDummy.releaseDate, movie.releaseDate)
        assertEquals(movieDummy.posterPath, movie.posterPath)
        assertEquals(movieDummy.backdropPath, movie.backdropPath)
        assertEquals(movieDummy.originalLanguage, movie.originalLanguage)
        assertEquals(movieDummy.originalTitle, movie.originalTitle)
        assertEquals(movieDummy.popularity.toString(), movie.popularity.toString())
        assertEquals(movieDummy.voteAverage.toString(), movie.voteAverage.toString())
        assertEquals(movieDummy.favorite, movie.favorite)
    }

}