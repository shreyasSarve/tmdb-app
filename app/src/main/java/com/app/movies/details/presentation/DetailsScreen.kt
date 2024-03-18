package com.app.movies.details.presentation

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.app.movies.R
import com.app.movies.core.components.NetworkImageLoader
import com.app.movies.datasamples.SampleData
import com.app.movies.details.domain.model.Image
import com.app.movies.details.presentation.components.RatingWithVotes
import com.app.movies.details.presentation.components.TagTile
import com.app.movies.details.presentation.components.VideoTile
import com.app.movies.moviesList.data.remote.MoviesApi
import com.app.movies.ui.theme.MoviesTheme
import com.app.movies.util.VideoPlayerUrls

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailsScreen(
    detailsState: DetailsState,
    onEvent: (DetailsScreenUiEvent) -> Unit
) {
    val backDropUri = (MoviesApi.IMAGE_BASE_URL + detailsState.movie?.backdrop_path).toUri()
    val posterImageUri = (MoviesApi.IMAGE_BASE_URL + detailsState.movie?.poster_path).toUri()


   Scaffold(
       topBar = {
           TopAppBar(
               navigationIcon = {
                   Icon(
                       imageVector = Icons.Default.ArrowBack,
                       contentDescription = "Back Button",
                       modifier = Modifier
                           .padding(5.dp)
                           .clickable {  }
                   )
                },
               colors = topAppBarColors(
                   containerColor = Color.Transparent,

               ),
               title = {  }
           )
       }
   ) {
       Box(
           modifier = Modifier
               .padding(it)
               .fillMaxSize()
       ){
           NetworkImageLoader(
               uri = backDropUri,
               contentDescription = detailsState.movie?.title,
               modifier = Modifier
                   .fillMaxSize()
           )
           Column(
               modifier = Modifier
                   .fillMaxSize()
                   .verticalScroll(rememberScrollState())
                   .background(Color.Black.copy(0.5f)),
           ) {

               Spacer(modifier = Modifier.height(20.dp))
               Row(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(16.dp),
                   verticalAlignment = Alignment.Top
               ) {
                   Box(
                       modifier = Modifier
                           .width(100.dp)
                           .height(150.dp)
                   ) {
                       NetworkImageLoader(
                           uri = posterImageUri,
                           contentDescription = detailsState.movie?.title,
                           modifier = Modifier
                               .fillMaxSize()
                               .padding(horizontal = 6.dp)
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
                               fontSize = 19.sp,
                               fontWeight = FontWeight.Bold
                           )
                           Spacer(modifier = Modifier.height(6.dp))

                           Spacer(modifier = Modifier.height(8.dp))

                           TagTile(
                               tag = movie.original_language.uppercase(),
                           )
                           Spacer(modifier = Modifier.height(5.dp))
                           TagTile(
                               tag = movie.release_date,
                               icon = {
                                   Icon(
                                       imageVector = Icons.Default.CalendarMonth,
                                       contentDescription = movie.release_date,
                                       tint = MaterialTheme.colorScheme.onBackground
                                   )
                               }
                           )
                           Spacer(modifier = Modifier.height(12.dp))
                           RatingWithVotes(movie = movie)

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
               Spacer(modifier = Modifier.height(32.dp))
               DisplayImages(
                   detailsState = detailsState,
                   onTabChanged = {index -> onEvent(DetailsScreenUiEvent.ChangeTab(index))}
               )
               Spacer(modifier = Modifier.height(32.dp))
           }

       }
   }
}


@Composable
fun DisplayImages(
    detailsState: DetailsState,
    onTabChanged: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(R.string.images),
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(10.dp))
        TabRow(
            selectedTabIndex = detailsState.selectedImageIndex,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    color = Color.White,
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[detailsState.selectedImageIndex])
                )
            }
        ) {
            ImageTabs.entries.forEachIndexed{ index, imageTabs ->
                Tab(
                    selected = detailsState.selectedImageIndex == index,
                    onClick = {onTabChanged(index)},
                    text = { Text(imageTabs.title) },
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxHeight()){
            when(ImageTabs.entries[detailsState.selectedImageIndex]){
                ImageTabs.BACKDROPS -> {
                    RowImageList(
                        images = detailsState.images?.backdrops?: emptyList(),
                        contentDescription = "Backdrop Images"
                    )
                }
                ImageTabs.POSTERS -> {
                    RowImageList(
                        images = detailsState.images?.posters?: emptyList(),
                        contentDescription = "Poster Images"
                    )
                }
                ImageTabs.LOGOS -> {
                    RowImageList(
                        images = detailsState.images?.logos?: emptyList(),
                        contentDescription = "Logos"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Composable
fun RowImageList(
    images:List<Image>,
    contentDescription: String?
) {
    LazyRow(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = 20.dp
        )
    ) {
        items(images.size){ index ->
            Box(
                modifier = Modifier.padding(
                    horizontal = 10.dp
                )
            ) {
                val imageUri = (MoviesApi.IMAGE_BASE_URL + images[index].imageUrl).toUri()
                NetworkImageLoader(
                    uri = imageUri,
                    contentDescription = contentDescription
                )
            }
        }
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



@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DetailsScreenPreview(){
    val state = DetailsState(
        movie = SampleData.movies[0],
        videos = emptyList(),
        isLoading = false
    )
    MoviesTheme {
        DetailsScreen(
            detailsState = state,
            onEvent = {}
        )

    }
}
