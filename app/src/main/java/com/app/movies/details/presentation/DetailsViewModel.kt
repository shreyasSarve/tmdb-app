package com.app.movies.details.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.movies.details.domain.repository.VideoListRepository
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
    private val videoListRepository: VideoListRepository,
    savedStateHandle: SavedStateHandle
):ViewModel(){

    private val movieId = savedStateHandle.get<Int>("movieId")
    private val _detailsState = MutableStateFlow(DetailsState())
    val detailsState = _detailsState.asStateFlow()

    init {
        getMovie(movieId?:-1)
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
            videoListRepository.getVideosList(
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
                            Log.d("Videos",videos.toString())
                        }
                    }
                }

            }
        }
    }
}