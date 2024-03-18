package com.app.movies.details.data.mapper

import com.app.movies.details.data.remote.respond.image.BackdropDTO
import com.app.movies.details.data.remote.respond.image.ImageListtDTO
import com.app.movies.details.data.remote.respond.image.LogoDTO
import com.app.movies.details.data.remote.respond.image.PosterDTO
import com.app.movies.details.domain.model.Images
import com.app.movies.details.domain.model.image.Backdrop
import com.app.movies.details.domain.model.image.Logo
import com.app.movies.details.domain.model.image.Poster


fun BackdropDTO.toBackdrop():Backdrop{
    return Backdrop(
        aspect_ratio = aspect_ratio ?: 0.0,
        file_path = file_path ?: "",
        height = height ?: 0,
        iso_639_1 = iso_639_1 ?: "",
        vote_average = vote_average ?: 0.0,
        vote_count = vote_count ?: 0,
        width = width ?: 0
    )
}

fun LogoDTO.toLogo(
)   :Logo   {
    return Logo(
        aspect_ratio = aspect_ratio ?: 0.0,
        file_path = file_path ?: "",
        height = height ?: 0,
        iso_639_1 = iso_639_1 ?: "",
        vote_average = vote_average ?: 0.0,
        vote_count = vote_count ?: 0,
        width = width ?: 0
    )
}

fun PosterDTO.toPoster(
)   :   Poster{
    return Poster(
        aspect_ratio = aspect_ratio ?: 0.0,
        file_path = file_path ?: "",
        height = height ?: 0,
        iso_639_1 = iso_639_1 ?: "",
        vote_average = vote_average ?: 0.0,
        vote_count = vote_count ?: 0,
        width = width ?: 0
    )
}

fun ImageListtDTO.toImages(
):Images{
    return Images(
        backdrops = backdrops?.map { it.toBackdrop() }?: emptyList(),
        posters = posters?.map { it.toPoster() }?: emptyList(),
        logos = logos?.map { it.toLogo() }?: emptyList()
    )
}