package com.app.movies.details.data.remote.respond

data class VideoListDTO(
    val id: Int,
    val results: List<VideoDTO>
)