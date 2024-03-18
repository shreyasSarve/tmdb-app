package com.app.movies.details.domain.model.image

import com.app.movies.details.domain.model.Image

data class Logo(
    val aspect_ratio: Double,
    val file_path: String,
    val height: Int,
    val iso_639_1: String,
    val vote_average: Double,
    val vote_count: Int,
    val width: Int
):Image(file_path)