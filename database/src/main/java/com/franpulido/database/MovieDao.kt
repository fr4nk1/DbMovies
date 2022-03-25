package com.franpulido.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.franpulido.database.models.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieEntity")
    fun getAll(): List<MovieEntity>

    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    fun findById(id: Int): MovieEntity

    @Query("SELECT COUNT(id) FROM MovieEntity")
    fun movieCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movies: List<MovieEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)
}