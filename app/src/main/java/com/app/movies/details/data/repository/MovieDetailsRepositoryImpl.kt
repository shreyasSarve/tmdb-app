package com.app.movies.details.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.app.movies.details.data.mapper.toImages
import com.app.movies.details.data.mapper.toVideo
import com.app.movies.details.data.mapper.toVideoEntity
import com.app.movies.details.domain.model.Images
import com.app.movies.details.domain.model.Video
import com.app.movies.details.domain.repository.MovieDetailsRepository
import com.app.movies.moviesList.data.local.movie.MovieDatabase
import com.app.movies.moviesList.data.remote.MoviesApi
import com.app.movies.moviesList.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val movieDatabase: MovieDatabase
): MovieDetailsRepository {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getVideosList(
        referenceId: Int,
        type: String
    ): Flow<Resource<List<Video>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))

            val videosFromLocal = movieDatabase.videoDao.getMovieByReferenceId(referenceId)
            if(videosFromLocal.isNotEmpty()){
                val videoList=videosFromLocal.map { it.toVideo() }
                emit(
                    Resource.Success(
                        data = videoList
                    )
                )
                emit(Resource.Loading(isLoading = false))
                return@flow
            }

            val videosFromRemote = try {
                moviesApi.getVideos(
                    type = type,
                    id = referenceId
                )
            }catch (e:IOException){
                emit(
                    Resource.Error(
                        message = "Error occurred while loading videos"
                    )
                )
                emit(Resource.Loading(isLoading = false))
                return@flow
            }catch (e:HttpException){
                emit(
                    Resource.Error(
                        message = "Error occurred while loading videos"
                    )
                )
                emit(Resource.Loading(isLoading = false))
                return@flow
            }catch (e:Exception){
                emit(
                    Resource.Error(
                        message = "Error occurred while loading videos"
                    )
                )
                emit(Resource.Loading(isLoading = false))
                return@flow
            }

            val videosToLocal = videosFromRemote.results.map { videoDTO ->
                videoDTO.toVideoEntity(referenceId)
            }

            movieDatabase.videoDao.upsertVideosList(videosToLocal)

            emit(
                Resource.Success(
                    data = videosToLocal.map { it.toVideo() }
                )
            )
            emit(Resource.Loading(isLoading = false))
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getImages(
        referenceId: Int,
        type: String
    ): Flow<Resource<Images>> {
       return flow {
           emit(Resource.Loading(isLoading = true))

           val imagesFromRemote = try {
               moviesApi.getImages(
                   id = referenceId,
                   type = type
               )
           }catch (e:IOException){
                emit(
                    Resource.Error(
                        message = "Error occurred while loading videos"
                    )
                )
                emit(Resource.Loading(isLoading = false))
                return@flow
            }catch (e:HttpException){
                emit(
                    Resource.Error(
                        message = "Error occurred while loading videos"
                    )
                )
                emit(Resource.Loading(isLoading = false))
                return@flow
            }catch (e:Exception){
                emit(
                    Resource.Error(
                        message = "Error occurred while loading videos"
                    )
                )
                emit(Resource.Loading(isLoading = false))
                return@flow
            }
           emit(
               Resource.Success(
                     data = imagesFromRemote.toImages()
                )
           )
           emit(Resource.Loading(isLoading = false))
       }
    }
}