package com.app.movies.moviesList.domain.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter


data class Movie(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    val id:Int,
    val category: String
){
    fun getReleaseYear() : String {
      return release_date.take(4)
    }
    val isRelease:Boolean
        @RequiresApi(Build.VERSION_CODES.O)
        get(){
            val releaseDate = LocalDate.parse(release_date, DateTimeFormatter.ISO_DATE)
            val currentDate = LocalDate.now()
            return releaseDate.isBefore(currentDate) || releaseDate.isEqual(currentDate)
        }
}
