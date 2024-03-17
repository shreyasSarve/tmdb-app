package com.app.movies.details.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.app.movies.R
import com.app.movies.moviesList.data.remote.MoviesApi
import com.app.movies.moviesList.domain.util.RatingBar

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel(),
) {
    val detailsState = viewModel.detailsState.collectAsState().value

    val backDropImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(MoviesApi.IMAGE_BASE_URL + detailsState.movie?.backdrop_path).size(Size.ORIGINAL)
            .build()
    ).state

    val posterImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(MoviesApi.IMAGE_BASE_URL + detailsState.movie?.poster_path).size(Size.ORIGINAL)
            .build()
    ).state
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        when (backDropImageState) {
            AsyncImagePainter.State.Empty -> Unit
            is AsyncImagePainter.State.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .height(250.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ImageNotSupported,
                        contentDescription = detailsState.movie?.title
                    )
                }
            }

            is AsyncImagePainter.State.Loading -> Unit
            is AsyncImagePainter.State.Success -> {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .height(220.dp),
                    painter = backDropImageState.painter,
                    contentDescription = detailsState.movie?.title,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(6.dp))
            }

        }
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .height(140.dp)
            ) {
                when (posterImageState) {
                    AsyncImagePainter.State.Empty -> Unit
                    is AsyncImagePainter.State.Error -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(6.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ImageNotSupported,
                                contentDescription = detailsState.movie?.title
                            )
                        }
                    }

                    is AsyncImagePainter.State.Loading -> Unit
                    is AsyncImagePainter.State.Success -> {
                        Image(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(6.dp)
                                .clip(RoundedCornerShape(6.dp)),
                            painter = posterImageState.painter,
                            contentDescription = detailsState.movie?.title,
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                    }

                }
            }
            detailsState.movie?.let { movie ->

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = movie.title,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp)
                    ) {
                        RatingBar(
                            starsModifier = Modifier.size(18.dp),
                            rating = movie.vote_average / 2
                        )
                        Text(
                            text = movie.vote_average.toString().take(3),
                            modifier = Modifier.padding(16.dp),
                            color = Color.LightGray,
                            fontSize = 14.sp,
                            maxLines = 1,
                        )
                    }
                    Spacer(modifier = Modifier.height(18.dp))
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
    }
}