package com.app.movies.details.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface VideoDao {

    @Upsert
    suspend fun upsertVideosList(videos:List<VideoEntity>)

    @Query("SELECT * FROM videoentity WHERE referenceId = :referenceId")
    suspend fun getMovieByReferenceId(referenceId: Int):List<VideoEntity>
}