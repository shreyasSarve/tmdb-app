package com.app.movies.details.presentation.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.app.movies.core.components.NetworkImageLoader
import com.app.movies.details.domain.model.Video
import com.app.movies.util.VideoPlayerUrls


@Composable
fun VideoTile(
    video: Video
) {

    val context = LocalContext.current
    val ytUri = VideoPlayerUrls.getYoutubeThumbnailUrl(video.key).toUri()

    Box(
        modifier = Modifier
            .width(200.dp)
            .height(150.dp),
        contentAlignment = Alignment.Center
    ){
        NetworkImageLoader(
            uri = ytUri,
            contentDescription = video.name,
            modifier = Modifier
                .fillMaxSize()
        )
        Icon(
            imageVector = Icons.Default.PlayCircle,
            contentDescription = "Play Video",
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .align(Alignment.Center)
                .clickable {
                    val videoPlayableLink =
                        VideoPlayerUrls.getYoutubeVideoLink(video.key)
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoPlayableLink))
                    intent.putExtra(
                        "force_fullscreen",
                        true
                    )
                    intent.putExtra(
                        "finish_on_ended",
                        true
                    )
                    context.startActivity(intent)
                },
        )
    }
}