package com.app.movies.details.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.movies.moviesList.domain.model.Movie
import com.app.movies.moviesList.domain.util.RatingBar

@Composable
fun RatingWithVotes(
    movie: Movie
) {
    Row(
        modifier = Modifier
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RatingBar(
            rating = movie.vote_average
        )
        Spacer(modifier = Modifier.width(5.dp))
        Column {
            Text(
                text = "User Score",
                color = Color.White,
                fontSize = 15.sp,
                maxLines = 1,
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = "(${movie.vote_count} votes)",
                color = Color.White,
                fontSize = 13.sp,
            )
        }
    }
}
