package com.app.movies.util

import androidx.compose.ui.graphics.Color
import com.app.movies.ui.theme.RatingColors

object RatingUtils {
    const val MAX_RATINGS = 10.0

    private const val RATING_FORMATTER = "%.1f"
    fun getRatingColor(
        rating:Double,
        released : Boolean = true
    ):Color{
        if(!released) return RatingColors.NOT_RELEASED
        if(rating >= 7) return RatingColors.GOOD
        if(rating >=5) return RatingColors.AVERAGE
        return RatingColors.BAD
    }

    fun getFormattedRating(
        rating : Double,
        released: Boolean = true
    ) :String {
        if(!released) return "NR"
        if(rating>= MAX_RATINGS) return MAX_RATINGS.toInt().toString()
        return RATING_FORMATTER.format(rating)
    }
}