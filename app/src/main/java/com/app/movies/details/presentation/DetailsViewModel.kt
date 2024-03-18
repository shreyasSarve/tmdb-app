package com.app.movies.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.movies.details.domain.repository.MovieDetailsRepository
import com.app.movies.moviesList.domain.repository.MovieListRepository
import com.app.movies.moviesList.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: MovieListRepository,
    private val movieDetailsRepository: MovieDetailsRepository,
    savedStateHandle: SavedStateHandle
):ViewModel(){

    private val movieId = savedStateHandle.get<Int>("movieId")
    private val _detailsState = MutableStateFlow(DetailsState())
    val detailsState = _detailsState.asStateFlow()

    init {
        getMovie(movieId?:-1)
        getImages(movieId?:-1)
    }

    private fun getMovie(movieId: Int) {
        viewModelScope.launch {
            _detailsState.update {
                it.copy(
                    isLoading = true
                )
            }
            repository.getMovie(movieId).collect{   movieResult ->
                when(movieResult)   {
                    is Resource.Error -> {
                        _detailsState.update { it.copy(isLoading = false) }
                    }
                    is Resource.Loading -> {
                        _detailsState.update { it.copy(isLoading = movieResult.isLoading) }
                    }
                    is Resource.Success -> {
                        movieResult.data?.let { movie ->
                            _detailsState.update { it.copy(movie = movie) }
                        }
                    }
                }

            }
            movieDetailsRepository.getVideosList(
                movieId,
                type="movie"
            ).collect{videoResult->
                when(videoResult)   {
                    is Resource.Error -> {
                        _detailsState.update { it.copy(isLoading = false) }
                    }
                    is Resource.Loading -> {
                        _detailsState.update { it.copy(isLoading = videoResult.isLoading) }
                    }
                    is Resource.Success -> {
                        videoResult.data?.let { videos ->
                            _detailsState.update { it.copy(videos = videos ) }
                        }
                    }
                }

            }
        }
    }

    fun onEvent(event: DetailsScreenUiEvent){
        when(event){
            is DetailsScreenUiEvent.ChangeTab -> {
                _detailsState.update { it.copy(selectedImageIndex = event.index) }
            }
        }
    }
    private fun getImages(movieId: Int){
        viewModelScope.launch {
            movieDetailsRepository.getImages(
                referenceId = movieId,
                type = "movie"
            ).collect{imageResult ->
                when(imageResult)   {
                    is Resource.Error -> {
                        _detailsState.update { it.copy(isLoading = false) }
                    }
                    is Resource.Loading -> {
                        _detailsState.update { it.copy(isLoading = imageResult.isLoading) }
                    }
                    is Resource.Success -> {
                        imageResult.data?.let { images ->
                            _detailsState.update { it.copy(images = images ) }
                        }
                    }
                }
            }
        }
    }
}