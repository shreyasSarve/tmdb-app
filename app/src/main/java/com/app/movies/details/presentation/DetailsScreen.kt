package com.app.movies.details.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.movies.R
import com.app.movies.core.components.NetworkImageLoader
import com.app.movies.details.presentation.components.VideoTile
import com.app.movies.moviesList.data.remote.MoviesApi
import com.app.movies.moviesList.domain.util.RatingBar
import com.app.movies.util.VideoPlayerUrls

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel(),
) {
    val detailsState = viewModel.detailsState.collectAsState().value
    val backDropUri = (MoviesApi.IMAGE_BASE_URL + detailsState.movie?.backdrop_path).toUri()
    val posterImageUri = (MoviesApi.IMAGE_BASE_URL + detailsState.movie?.poster_path).toUri()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        NetworkImageLoader(
            uri = backDropUri,
            contentDescription = detailsState.movie?.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .height(220.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .height(240.dp)
            ) {
                NetworkImageLoader(
                    uri = posterImageUri,
                    contentDescription = detailsState.movie?.title,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp)
                        .clip(RoundedCornerShape(6.dp))
                )
            }
            detailsState.movie?.let { movie ->

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = movie.title,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RatingBar(
                            rating = movie.vote_average
                        )
                        Text(
                            text = movie.vote_average.toString().take(3),
                            modifier = Modifier.padding(16.dp),
                            color = Color.White,
                            fontSize = 19.sp,
                            maxLines = 1,
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = stringResource(R.string.language) + movie.original_language,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = stringResource(R.string.release_date) + movie.release_date,
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = movie.vote_count.toString() + stringResource(R.string.votes),
                    )

                }
            }
        }

        detailsState.movie?.let { movie ->
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = stringResource(R.string.overview),
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = movie.overview,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))

        DisplayVideos(
            detailsState = detailsState
        )
    }


}

@Composable
fun DisplayVideos(
    detailsState :DetailsState
){
    Text(
        modifier = Modifier.padding(start = 16.dp),
        text = stringResource(R.string.videos),
        fontSize = 19.sp,
        fontWeight = FontWeight.SemiBold
    )

    detailsState.videos?.let { videos ->
        if(videos.isEmpty()) return@let
        val ytVideos = videos.filter {video->
            return@filter video.site.lowercase() == VideoPlayerUrls.YT
        }
        LazyRow(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(
                horizontal = 20.dp
            )
        ) {
            items(ytVideos.size){index ->
                Box(modifier = Modifier.padding(
                    horizontal = 10.dp
                )
                ) {
                    VideoTile(
                        video = ytVideos[index]
                    )
                }
            }
        }
    }
}