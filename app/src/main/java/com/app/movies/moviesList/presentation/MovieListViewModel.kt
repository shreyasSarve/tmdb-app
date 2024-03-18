package com.app.movies.moviesList.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.movies.moviesList.domain.repository.MovieListRepository
import com.app.movies.moviesList.domain.util.Category
import com.app.movies.moviesList.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieListViewModel @Inject constructor(
  private val repository: MovieListRepository,
) :ViewModel() {

    private var _mainState = MutableStateFlow(MovieListState())
    val mainState = _mainState.asStateFlow()


     init {
         getPopularMovies(false)
         getUpcomingMovies(false)
     }


    fun onEvent(event:MovieListUiEvent){
        when(event){
            MovieListUiEvent.Navigate -> {
                _mainState.update {
                    it.copy(
                        isCurrentPopularScreen = !mainState.value.isCurrentPopularScreen
                    )
                }
            }
            is MovieListUiEvent.Paginate -> {
                    when(event.category){
                         Category.POPULAR -> getPopularMovies(true)
                         Category.UPCOMING -> getUpcomingMovies(true)
                    }
            }
        }
    }
    private fun getPopularMovies(
        forceRemoteFetch:Boolean
    ) {
        viewModelScope.launch {
            _mainState.update {
                it.copy(
                    isLoading = true
                )
            }

            repository.getMoviesList(
                forceRemoteFetch = forceRemoteFetch,
                category = Category.POPULAR,
                page = mainState.value.popularMovieListPage
            ).collect{result->
                when(result){
                    is Resource.Error -> {
                        _mainState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _mainState.update {
                            it.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { data ->
                            _mainState.update { movieState ->
                                movieState.copy(
                                    popularMovieList =
                                    movieState.popularMovieList + data.shuffled(),
                                    popularMovieListPage = movieState.popularMovieListPage + 1
                                )
                            }
                        }
                    }
                }

            }
        }
    }

    private fun getUpcomingMovies(
        forceRemoteFetch: Boolean
    ) {
        viewModelScope.launch {
            _mainState.update {
                it.copy(
                    isLoading = true
                )
            }

            repository.getMoviesList(
                forceRemoteFetch = forceRemoteFetch,
                category = Category.UPCOMING,
                page = mainState.value.upcomingMovieListPage
            ).collect{ result ->
                when(result){
                    is Resource.Error -> {
                        _mainState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _mainState.update {
                            it.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { data ->
                            _mainState.update { movieState ->
                                movieState.copy(
                                    upcomingMovieList =
                                    movieState.upcomingMovieList + data.shuffled(),
                                    upcomingMovieListPage = movieState.upcomingMovieListPage + 1
                                )
                            }
                        }
                    }
                }

            }
        }
    }

}