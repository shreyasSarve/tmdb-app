package com.app.movies.moviesList.data.remote

import com.app.movies.moviesList.data.remote.respond.MoviesListDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/{category}")
    suspend fun getMoviesList(
        @Path("category") category: String,
        @Query("page") page:Int,
        @Query("api_key") apiKey:String= API_KEY
    ) : MoviesListDTO

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val API_KEY="a2bb4ca8bb48320753a272e7bdea7d3d"
    }
}