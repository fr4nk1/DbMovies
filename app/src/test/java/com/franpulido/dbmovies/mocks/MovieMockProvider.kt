package com.franpulido.dbmovies.mocks

import com.franpulido.database.models.MovieEntity
import com.franpulido.domain.models.Movie

class MovieMockProvider {

    companion object {

        private const val ID = 1
        private const val TITLE = "Spiderman"
        private const val OVERVIEW = "Spiderman description"
        private const val RELEASE_DATE = "2021"
        private const val POSTER_PATH = "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg"
        private const val BACKDROP_PATH = "/iQFcwSGbZXMkeyKrxbPnwnRo5fl.jpg"
        private const val ORIGINAL_LANGUAGE = "en"
        private const val ORIGINAL_TITLE = "Spiderman"
        private const val POPULARITY = 9461.50
        private const val VOTE_AVERAGE = 8.4
        private const val FAVORITE = false

        fun getDummyMovie() = Movie(
            ID, TITLE, OVERVIEW, RELEASE_DATE, POSTER_PATH,
            BACKDROP_PATH, ORIGINAL_LANGUAGE, ORIGINAL_TITLE, POPULARITY, VOTE_AVERAGE, FAVORITE
        )

        fun getDummyMovieEntity() = MovieEntity(
            ID, TITLE, OVERVIEW, RELEASE_DATE, POSTER_PATH,
            BACKDROP_PATH, ORIGINAL_LANGUAGE, ORIGINAL_TITLE, POPULARITY, VOTE_AVERAGE, FAVORITE
        )

    }

}
