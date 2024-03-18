package com.app.movies.details.presentation

import com.app.movies.details.domain.model.Video
import com.app.movies.moviesList.domain.model.Movie

data class DetailsState(
    val isLoading:Boolean=false,
    val movie: Movie?=null,
    val videos:List<Video>?=null
)