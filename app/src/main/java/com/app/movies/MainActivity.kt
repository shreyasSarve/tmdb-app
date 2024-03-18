package com.app.movies

import Screen
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.movies.core.presentation.HomeScreen
import com.app.movies.details.presentation.DetailsScreen
import com.app.movies.details.presentation.DetailsViewModel
import com.app.movies.ui.theme.MoviesTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesTheme {
                SetBarColor(
                    color = MaterialTheme.colorScheme.inverseOnSurface
                )
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route
                    ){
                        composable(
                            route = Screen.Home.route,

                        ){
                            HomeScreen(
                                navController
                            )
                        }
                        composable(
                            route = Screen.Details.route + "/{movieId}",
                            arguments = listOf(
                                navArgument("movieId"){
                                    type = NavType.IntType
                                    nullable = false
                                }
                            )

                            ){
                            val viewModel:DetailsViewModel = hiltViewModel()
                            DetailsScreen(
                                detailsState = viewModel.detailsState.collectAsState().value,
                                onEvent = viewModel::onEvent,
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SetBarColor(color: Color){

        val systemUiController = rememberSystemUiController()
        LaunchedEffect(color) {
            systemUiController.setSystemBarsColor(color)
        }

    }
}
