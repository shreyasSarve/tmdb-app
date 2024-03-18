package com.app.movies.details.domain.model

import android.net.Uri
import androidx.core.net.toUri


abstract class Image(
    val imageUrl: String
){
    val image_uri:Uri
        get(){
            return imageUrl.toUri()
        }
}