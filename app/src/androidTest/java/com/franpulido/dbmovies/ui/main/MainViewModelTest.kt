package com.franpulido.dbmovies.ui.main

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.franpulido.data.datasource.TheMovieDbDataSource
import com.franpulido.data.repository.MoviesDataRepository
import com.franpulido.database.MovieDatabase
import com.franpulido.database.datasource.RoomDataSource
import com.franpulido.dbmovies.R
import com.franpulido.domain.usecases.GetPopularMovies
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainViewModelTest : TestCase() {

    private lateinit var viewModel: MainViewModel

    @Before
    public override fun setUp() {
        super.setUp()

        val context = ApplicationProvider.getApplicationContext<Context>()

        val db = Room.inMemoryDatabaseBuilder(context, MovieDatabase::class.java).allowMainThreadQueries().build()

        val localDataSource = RoomDataSource(db)
        val remoteDataSource = TheMovieDbDataSource()
        val moviesRepository = MoviesDataRepository(localDataSource, remoteDataSource, context.getString(
            R.string.api_key))
        val getPopularMovies = GetPopularMovies(moviesRepository)
        viewModel = MainViewModel(getPopularMovies)
    }

    @Test
    fun testViewModelNotNull(){
        /*viewModel.initUi()
        val result: MainViewModel.UiModel = viewModel.model.getOrAwaitValue()
        assertThat(result != null).isTrue()*/
    }

}