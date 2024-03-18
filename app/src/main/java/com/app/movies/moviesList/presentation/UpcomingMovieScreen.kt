package com.app.movies.moviesList.presentation

import Screen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.movies.datasamples.SampleData
import com.app.movies.moviesList.domain.util.Category
import com.app.movies.moviesList.presentation.components.MovieItem
import com.app.movies.ui.theme.MoviesTheme

@Composable
fun UpcomingMoviesScreen(
    movieState: MovieListState,
    onNavigate:(String) ->Unit,
    onEvent: (MovieListUiEvent)->Unit
){
    when(movieState.upcomingMovieList.isEmpty()){
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
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
            ) {
                items(movieState.upcomingMovieList.size) {index->
                    MovieItem(
                        movie = movieState.upcomingMovieList[index],
                        onShowDetails = { movieId ->
                            onNavigate(Screen.Details.route + "/${movieId}")
                        },
                        modifier = Modifier
                            .padding(horizontal = 15.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    if(index >= movieState.upcomingMovieList.size -1 && !movieState.isLoading){
                        onEvent(MovieListUiEvent.Paginate(
                            category = Category.UPCOMING
                        )
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun UpcomingMoviesScreenPreview() {
    val movieState  = MovieListState(
        upcomingMovieList = SampleData.movies
    )
    MoviesTheme {
        UpcomingMoviesScreen(
            movieState = movieState,
            onEvent = {},
            onNavigate = {}
        )
    }
}
