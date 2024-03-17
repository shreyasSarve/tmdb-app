package com.app.movies.moviesList.data.remote.respond

data class MoviesListDTO(
    val page: Int,
    val results: List<MovieDTO>,
    val total_pages: Int,
    val total_results: Int
)