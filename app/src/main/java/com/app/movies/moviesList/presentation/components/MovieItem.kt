package com.app.movies.moviesList.presentation.components


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.app.movies.core.components.NetworkImageLoader
import com.app.movies.datasamples.SampleData
import com.app.movies.moviesList.data.remote.MoviesApi
import com.app.movies.moviesList.domain.model.Movie
import com.app.movies.moviesList.domain.util.RatingBar
import com.app.movies.ui.theme.MoviesTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieItem(
    movie: Movie,
    modifier: Modifier,
    onShowDetails:(Int) -> Unit,
) {

    val imageUri = (MoviesApi.IMAGE_BASE_URL + movie.poster_path).toUri()
    Column(
        modifier = modifier
            .wrapContentHeight()
            .width(50.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                onShowDetails(movie.id)
            }
    ) {
            Box{
                NetworkImageLoader(
                    uri =  imageUri,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                Box(
                    modifier = Modifier
                        .padding(start = 2.dp, top = 120.dp)
                        .align(Alignment.BottomStart)
                ){
                    RatingBar(
                        rating = movie.vote_average,
                        released = movie.isRelease,
                        modifier = Modifier
                            .size(40.dp)

                    )
                }
            }
        Column(
            modifier = Modifier
                .padding(horizontal = 0.dp, vertical = 5.dp),
        ) {
            Text(
                text = movie.title,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = movie.getReleaseYear(),
                color = Color.White,
                fontSize = 12.sp,
                maxLines = 1,
            )
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MovieItemPreview(){
    val movie = SampleData.movies[0]
    MoviesTheme {
        MovieItem(
            movie = movie,
            onShowDetails = {},
            modifier = Modifier
                .padding(10.dp)
        )
    }
}

