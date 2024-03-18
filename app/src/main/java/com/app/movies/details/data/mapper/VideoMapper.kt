package com.app.movies.details.data.mapper

import com.app.movies.details.data.local.VideoEntity
import com.app.movies.details.data.remote.respond.video.VideoDTO
import com.app.movies.details.domain.model.Video


fun VideoEntity.toVideo() : Video{
    return Video(
    id = id ,
    iso_3166_1 = iso_3166_1,
    iso_639_1 = iso_639_1,
    key = key,
    name = name,
    official = official,
    published_at = published_at,
    site = site,
    size = size,
    type = type,
    referenceId = referenceId
    )
}



fun VideoDTO.toVideoEntity(
    referenceId:Int
):VideoEntity {
    return VideoEntity(
        id = id,
        iso_3166_1 = iso_3166_1?:"",
        iso_639_1 = iso_639_1?:"",
        key = key?:"",
        name = name?:"",
        official = official?:false,
        published_at = published_at?:"",
        site = site?:"",
        size = size ?: -1,
        type = type ?: "",
        referenceId = referenceId
    )
}