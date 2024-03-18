package com.app.movies.core.presentation

import Screen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Upcoming
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.movies.R
import com.app.movies.moviesList.presentation.MovieListUiEvent
import com.app.movies.moviesList.presentation.MovieListViewModel
import com.app.movies.moviesList.presentation.PopularMoviesScreen
import com.app.movies.moviesList.presentation.UpcomingMoviesScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
){
    val viewModel:MovieListViewModel = hiltViewModel()
    val movieState = viewModel.mainState.collectAsState().value
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                bottomNavController = bottomNavController,
                onEvent = viewModel::onEvent
            )
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when(movieState.isCurrentPopularScreen){
                            true -> stringResource(R.string.popular)
                            false -> stringResource(R.string.upcoming)
                        },
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.inverseSurface
                    )
                },
                modifier = Modifier
                    .shadow(2.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    MaterialTheme.colorScheme.inverseOnSurface
                )
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            NavHost(navController = bottomNavController,
                startDestination = Screen.PopularMovieList.route
            ){
                composable(Screen.PopularMovieList.route){
                    PopularMoviesScreen(
                        movieState = movieState,
                        onEvent = viewModel::onEvent,
                        onNavigate = {route-> navController.navigate(route)}
                    )
                }
                composable(Screen.UpcomingMovieList.route){
                    UpcomingMoviesScreen(
                        movieState = movieState,
                        onEvent = viewModel::onEvent,
                        onNavigate = {route-> navController.navigate(route)}
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(
    bottomNavController:NavController,
    onEvent:(MovieListUiEvent) -> Unit
){
    val items= listOf(
        BottomItem(
            title = stringResource(R.string.popular),
            icon = Icons.Default.Movie
        ),
        BottomItem(
            title = stringResource(R.string.upcoming),
            icon = Icons.Default.Upcoming
        )
    )
    
    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            items.forEachIndexed{ index,bottomItem->
            NavigationBarItem(
                selected = selected.intValue == index,
                onClick = {
                    selected.intValue = index
                    when(selected.intValue){
                        0 ->{
                            onEvent(MovieListUiEvent.Navigate)
                            bottomNavController.popBackStack()
                            bottomNavController.navigate(Screen.PopularMovieList.route)
                        }
                        1->{
                            onEvent(MovieListUiEvent.Navigate)
                            bottomNavController.popBackStack()
                            bottomNavController.navigate(Screen.UpcomingMovieList.route)
                        }
                        else -> Unit
                    }
                },
                icon = {
                    Icon(
                        imageVector = bottomItem.icon,
                        contentDescription = bottomItem.title,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                },
                label = {
                    Text(
                        text = bottomItem.title,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            )

            }
        }
    }
}

data class BottomItem(
    val title:String,
    val icon:ImageVector
)