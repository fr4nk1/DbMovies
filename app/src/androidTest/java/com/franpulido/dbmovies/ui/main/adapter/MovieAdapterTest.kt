package com.franpulido.dbmovies.ui.main.adapter

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.platform.app.InstrumentationRegistry
import com.franpulido.dbmovies.ui.main.compose.MovieAdapterItem
import com.franpulido.dbmovies.ui.main.compose.TITLE_TEST_TAG
import com.franpulido.dbmovies.ui.main.compose.VOTE_TEST_TAG
import com.franpulido.dbmovies.ui.models.MovieModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MovieAdapterTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val ctx = InstrumentationRegistry.getInstrumentation().targetContext

    companion object{
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

        fun getDummyMovie() = MovieModel(
            ID, TITLE, OVERVIEW, RELEASE_DATE, POSTER_PATH,
            BACKDROP_PATH, ORIGINAL_LANGUAGE, ORIGINAL_TITLE, POPULARITY, VOTE_AVERAGE, FAVORITE
        )
    }

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MovieAdapterItem(getDummyMovie(), ctx) {}
        }
    }

    @Test
    fun checkTitleAndVote(): Unit = with(composeTestRule) {
        onNodeWithTag(TITLE_TEST_TAG).assertTextContains("Spiderman")
        onNodeWithTag(VOTE_TEST_TAG).assertTextContains("8.4")
    }

}