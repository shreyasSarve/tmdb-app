package com.app.movies.moviesList.data.local.movie

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import java.util.Locale.Category

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertMoviesList(movies:List<MovieEntity>)

    @Query("SELECT * FROM moviesDB WHERE id = :id")
    suspend fun getMovieById(id:Int):MovieEntity?

    @Query("SELECT * FROM moviesDB WHERE category = :category")
    suspend fun getMovieByCategory(category:String):List<MovieEntity>
}