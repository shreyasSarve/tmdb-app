package com.app.movies.moviesList.domain.repository


import com.app.movies.moviesList.domain.model.Movie
import com.app.movies.moviesList.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {

    suspend fun getMoviesList(
        forceRemoteFetch:Boolean,
        category: String,
        page:Int
    ) : Flow<Resource<List<Movie>>>

    suspend fun getMovie(
        id:Int
    ) : Flow<Resource<Movie>>
}