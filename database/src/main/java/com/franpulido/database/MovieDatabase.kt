package com.franpulido.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.franpulido.database.models.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}