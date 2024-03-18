package com.app.movies.util

object VideoPlayerUrls {
        const val YT = "youtube"
        private const val YT_URL ="https://www.youtube.com/watch?v=\${videoId}"
        private const val YT_THUMBNAIL_URL="https://img.youtube.com/vi/\${videoId}/hqdefault.jpg"
        fun getYoutubeVideoLink(
            videoId:String
        ) : String  {
            return YT_URL.replace("\${videoId}",videoId)
        }

    fun getYoutubeThumbnailUrl(
        videoId: String
    ) : String  {
        return YT_THUMBNAIL_URL.replace("\${videoId}",videoId)
    }
}