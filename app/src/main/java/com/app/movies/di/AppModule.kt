package com.app.movies.di

import android.app.Application
import androidx.room.Room
import com.app.movies.moviesList.data.local.movie.MovieDatabase
import com.app.movies.moviesList.data.remote.MoviesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val interceptor:HttpLoggingInterceptor=HttpLoggingInterceptor().apply {
        level=HttpLoggingInterceptor.Level.BODY
    }
    private val client:OkHttpClient= OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun provideMovieApi() :MoviesApi {
        return Retrofit.Builder()
            .baseUrl(MoviesApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    fun providerMovieDatabase(
        app:Application
    ) :MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            "movie_database"
        ).build()
    }
}