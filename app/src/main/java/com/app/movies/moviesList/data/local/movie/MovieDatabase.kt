package com.app.movies.moviesList.data.local.movie

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.movies.details.data.local.VideoDao
import com.app.movies.details.data.local.VideoEntity


@Database(
    entities = [
        MovieEntity::class,
        VideoEntity::class
    ],
    version = 2,
)
abstract class MovieDatabase
    :RoomDatabase() {
    abstract val movieDao:MovieDao
    abstract val videoDao:VideoDao
}