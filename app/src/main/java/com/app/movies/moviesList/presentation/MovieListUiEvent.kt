package com.app.movies.moviesList.presentation

sealed class MovieListUiEvent {

    data class Paginate(
        val category: String
    ) :MovieListUiEvent()

    data object  Navigate :MovieListUiEvent()
}