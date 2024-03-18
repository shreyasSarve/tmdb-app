package com.app.movies.moviesList.domain.util

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.movies.ui.theme.MoviesTheme
import com.app.movies.util.RatingUtils

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    released: Boolean = true,
    maxRating: Double = RatingUtils.MAX_RATINGS
) {

    assert(rating<=maxRating){
        "Error"
    }
    val ratingColor = RatingUtils.getRatingColor(
        rating= rating,
        released = released
    )
    val progress = rating/maxRating
    val formattedRating = RatingUtils.getFormattedRating(
        rating = rating,
        released = released
    )
    Box(
        modifier = modifier
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = CircleShape
            )
            .background(Color.Black, CircleShape)
            .padding(1.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = progress.toFloat(),
            color = ratingColor,
            strokeCap = StrokeCap.Round,
            trackColor = ratingColor.copy(0.3f),
            strokeWidth = 5.dp,
        )
        Text(
            text = formattedRating,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Preview
@Composable
fun RatingBarPreview(){
    MoviesTheme {
        Box(
            modifier = Modifier
                .height(100.dp)
                .width(80.dp)
        ){
        RatingBar(
            rating = 5.123,
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.BottomStart)
        )
        }
    }
}