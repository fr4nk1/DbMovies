package com.franpulido.dbmovies.data.database

import androidx.room.*

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