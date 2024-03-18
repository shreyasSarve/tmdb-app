package com.app.movies.details.presentation

sealed class DetailsScreenUiEvent {
    class ChangeTab(val index:Int ):DetailsScreenUiEvent()
}