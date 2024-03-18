package com.app.movies.details.presentation

import com.app.movies.details.domain.model.Images
import com.app.movies.details.domain.model.Video
import com.app.movies.moviesList.domain.model.Movie

data class DetailsState(
    val isLoading:Boolean=false,
    val movie: Movie?=null,
    val videos:List<Video>?=null,
    val images:Images?=null,
    val selectedImageIndex:Int = 0
)

enum class ImageTabs(
     val title:String
){
    BACKDROPS("Backdrop"),
    POSTERS("Poster"),
    LOGOS("Logos");
}