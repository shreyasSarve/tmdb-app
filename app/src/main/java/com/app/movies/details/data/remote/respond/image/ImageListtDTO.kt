package com.app.movies.details.data.remote.respond.image

data class ImageListtDTO(
    val backdrops: List<BackdropDTO>?,
    val id: Int?,
    val logos: List<LogoDTO>?,
    val posters: List<PosterDTO>?
)