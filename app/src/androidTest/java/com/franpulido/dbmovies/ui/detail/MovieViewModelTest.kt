package com.franpulido.dbmovies.ui.detail

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.franpulido.data.repository.MoviesRepository
import com.franpulido.dbmovies.R
import com.franpulido.dbmovies.data.database.MovieDatabase
import com.franpulido.dbmovies.data.database.RoomDataSource
import com.franpulido.dbmovies.data.server.TheMovieDbDataSource
import com.franpulido.dbmovies.ui.main.getOrAwaitValue
import com.franpulido.data.usecases.FindMovieById
import com.franpulido.data.usecases.UpdateMovieFavorite
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MovieViewModelTest : TestCase() {

    private lateinit var viewModel: MovieViewModel

    @Before
    public override fun setUp() {
        super.setUp()

        val context = ApplicationProvider.getApplicationContext<Context>()

        val db = Room.inMemoryDatabaseBuilder(context, MovieDatabase::class.java).allowMainThreadQueries().build()

        val localDataSource = RoomDataSource(db)
        val remoteDataSource = TheMovieDbDataSource()
        val moviesRepository = MoviesRepository(localDataSource, remoteDataSource, context.getString(
            R.string.api_key))
        val findMovieById = FindMovieById(moviesRepository)
        val updateMovieFavorite = UpdateMovieFavorite(moviesRepository)
        viewModel = MovieViewModel(634649, findMovieById, updateMovieFavorite )
    }

    @Test
    fun testViewModelNotNull(){
        val result = viewModel.model.getOrAwaitValue()
        assertThat(result != null).isTrue()
    }

}