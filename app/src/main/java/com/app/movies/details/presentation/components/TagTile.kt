package com.app.movies.details.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.app.movies.ui.theme.MoviesTheme

@Composable
fun TagTile(
    modifier: Modifier = Modifier,
    tag:String,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    tagColor:Color = MaterialTheme.colorScheme.onBackground,
    icon:(@Composable () -> Unit)? = null
) {
    val radius  = 40.dp
    Row(
        modifier = modifier
            .clip(shape = RoundedCornerShape(radius))
            .background(backgroundColor, shape = RoundedCornerShape(radius))
            .padding(horizontal = 10.dp , vertical = 5.dp ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        icon?.invoke()
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = tag,
            maxLines = 1,
            color = tagColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis
        )
    }
}



@Preview
@Composable
fun TagTilePreview(){
    MoviesTheme {
       Surface(
           modifier = Modifier
               .background(Color.Black)
               .padding(12.dp)
       ) {
           TagTile(
               tag = "2024-12-11",
               modifier = Modifier,
               icon = {
                   Icon(
                       imageVector = Icons.Default.Calculate,
                       contentDescription = ""
                   )
               }
           )
       }
    }
}