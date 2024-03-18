package com.app.movies.details.data.remote.respond.video

import com.app.movies.details.data.remote.respond.video.VideoDTO

data class VideoListDTO(
    val id: Int,
    val results: List<VideoDTO>
)