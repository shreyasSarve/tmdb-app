package com.app.movies.details.domain.repository

import com.app.movies.details.domain.model.Images
import com.app.movies.details.domain.model.Video
import com.app.movies.moviesList.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {
    suspend fun getVideosList(
        referenceId:Int,
        type:String,
    ) : Flow<Resource<List<Video>>>

    suspend fun getImages(
        referenceId: Int,
        type: String,
    ) : Flow<Resource<Images>>
}