package com.app.movies.core.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size
import com.app.movies.ui.theme.MoviesTheme
import com.app.movies.util.VideoPlayerUrls
import com.valentinilk.shimmer.shimmer

@Composable
fun NetworkImageLoader(
    uri:Uri,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(uri)
            .size(Size.ORIGINAL)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build()
    ).state

    when(imageState){
        AsyncImagePainter.State.Empty -> {
            Box(
                modifier = modifier
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ImageNotSupported,
                    modifier = modifier,
                    contentDescription = contentDescription
                )
            }
        }
        is AsyncImagePainter.State.Success -> {
            Image(
                modifier = modifier,
                painter = imageState.painter,
                contentDescription = contentDescription,
                alignment = Alignment.Center,
                contentScale = ContentScale.FillHeight
            )
        }
        is AsyncImagePainter.State.Loading -> {
            Box(
                modifier = modifier
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
                    .shimmer(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = modifier
                        .background(
                            MaterialTheme.colorScheme.inverseSurface
                        )
                )
            }
        }
        is AsyncImagePainter.State.Error -> {
            Box(
                modifier = modifier
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ImageNotSupported,
                    modifier=modifier,
                    contentDescription = contentDescription
                )
            }
        }
    }
}


@Preview
@Composable
fun NetworkImagePreview(){
    val url = VideoPlayerUrls.getYoutubeThumbnailUrl("mH_LFkWxpI0")
    val uri = Uri.parse(url)
    MoviesTheme {
        NetworkImageLoader(
            uri = uri,
            contentDescription = "Youtube Thumbnail",
            modifier = Modifier
                .height(240.dp)
                .width(160.dp)
        )
    }
}