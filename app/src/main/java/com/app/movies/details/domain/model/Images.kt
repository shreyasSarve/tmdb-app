package com.app.movies.details.domain.model

import com.app.movies.details.domain.model.image.Backdrop
import com.app.movies.details.domain.model.image.Logo
import com.app.movies.details.domain.model.image.Poster

data class Images(
    val backdrops: List<Backdrop> = emptyList(),
    val posters: List<Poster> = emptyList(),
    val logos: List<Logo> = emptyList()
)
