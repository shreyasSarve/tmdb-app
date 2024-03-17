package com.app.movies.moviesList.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.movies.moviesList.domain.util.Category
import com.app.movies.moviesList.presentation.components.MovieItem

@Composable
fun PopularMoviesScreen(
    movieState:MovieListState,
    navController: NavHostController,
    onEvent: (MovieListUiEvent)->Unit
){
    when(movieState.popularMovieList.isEmpty()){
        true -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        false -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
                ) {
                    items(movieState.popularMovieList.size) {index->
                        MovieItem(
                            movie = movieState.popularMovieList[index],
                            navHostController = navController
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        if(index >= movieState.popularMovieList.size -1 && !movieState.isLoading){
                            onEvent(MovieListUiEvent.Paginate(
                                category = Category.POPULAR
                                )
                            )
                        }
                    }
                }
        }
    }
}