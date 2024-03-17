package com.app.movies.moviesList.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.app.movies.moviesList.data.local.movie.MovieDatabase
import com.app.movies.moviesList.data.mapper.toMovie
import com.app.movies.moviesList.data.mapper.toMovieEntity
import com.app.movies.moviesList.data.remote.MoviesApi
import com.app.movies.moviesList.domain.model.Movie
import com.app.movies.moviesList.domain.repository.MovieListRepository
import com.app.movies.moviesList.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val movieDatabase: MovieDatabase
) : MovieListRepository {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getMoviesList(
        forceRemoteFetch: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))

            val localMovieList = movieDatabase.movieDao.getMovieByCategory(category)

            val shouldLoadLocalMovies = localMovieList.isNotEmpty() && !forceRemoteFetch

            if(shouldLoadLocalMovies){
                emit(
                    Resource.Success(
                        data = localMovieList.map {
                            it.toMovie(category)
                        }
                    )
                )
                emit(Resource.Loading(isLoading = false))
                return@flow
            }

            val movieListRemote = try {
                moviesApi.getMoviesList(
                    category = category,
                    page = page
                )
            }catch (e:IOException){
                e.printStackTrace()
                emit(
                    Resource.Error(
                        message = "Error loading movies"
                    )
                )
                emit(Resource.Loading(isLoading = false))
                return@flow
            }catch (e:HttpException){
                e.printStackTrace()
                emit(
                    Resource.Error(
                        message = "Error loading movies"
                    )
                )
                emit(Resource.Loading(isLoading = false))
                return@flow
            }catch (e:Exception){
                e.printStackTrace()
                emit(
                    Resource.Error(
                        message = "Error loading movies"
                    )
                )
                emit(Resource.Loading(isLoading = false))
                return@flow
            }

            val movieEntities = movieListRemote.results.map {
                it.toMovieEntity(category)
            }
            movieDatabase.movieDao.upsertMoviesList(movieEntities)

            emit(
                Resource.Success(
                    data = movieEntities.map { it.toMovie(category) }
                )
            )
            emit(Resource.Loading(isLoading = false))

        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(isLoading = true))

            val movieEntity = movieDatabase.movieDao.getMovieById(id)
            movieEntity?.let { movie->
                emit(Resource.Success(
                    data = movie.toMovie(movie.category)
                    )
                )
                emit(Resource.Loading(isLoading = false))
                return@flow
            }
            emit(
                Resource
                    .Error(
                        message = "Error no such  movie"
                    )
            )
            emit(Resource.Loading(isLoading = false))
        }
    }
}