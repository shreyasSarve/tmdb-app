package com.app.movies.details.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VideoEntity(
    val iso_3166_1: String,
    val iso_639_1: String,
    val key: String,
    val name: String,
    val official: Boolean,
    val published_at: String,
    val site: String,
    val size: Int,
    val type: String,

    val referenceId:Int,
    @PrimaryKey val id: String,
)