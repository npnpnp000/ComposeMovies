package com.composemovies.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.composemovies.R
import com.composemovies.model.response_models.Result
import com.composemovies.utils.extensions.WindowInfo
import com.composemovies.utils.extensions.rememberWidowInfo

@Composable
fun StarRating(result: Result) {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        StarRatingSample(result.vote_average)
    }
}

@Composable
fun StarRatingSample(rating: Double) {

    var rememberRating by remember { mutableFloatStateOf((rating/2).toFloat()) }

    Log.e("rating", "popularity: "+rating+ "toFloat:" +rememberRating+ "toInt:" +rememberRating.toInt())

    StarRatingBar(
        maxStars = 5,
        rating = rememberRating,
        onRatingChanged = {
            rememberRating = it
        }
    )
}

@Composable
private fun getStarIcon(rating: Float, i: Int): ImageVector {

    if (rating.toInt()+1 == i && rating+1 > i ) return ImageVector.vectorResource(R.drawable.half_star)
    if (rating <= i) return ImageVector.vectorResource(R.drawable.empty_star)
    return ImageVector.vectorResource(R.drawable.star)
}

@Composable
fun StarRatingBar(
    maxStars: Int = 5,
    rating: Float,
    onRatingChanged: (Float) -> Unit
) {
    val widowInfo = rememberWidowInfo()
    val density = LocalDensity.current.density
    val starSize = (getStarSizeByScreenInfo(widowInfo) * density).dp
    val starSpacing = (0.5f * density).dp

    Row(
        modifier = Modifier.selectableGroup(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val icon = getStarIcon(rating,i)
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFFFFC700),
                modifier = Modifier
                    .width(starSize)
                    .height(starSize)
            )

            if (i < maxStars) {
                Spacer(modifier = Modifier.width(starSpacing))
            }
        }
    }
}

private fun getStarSizeByScreenInfo(widowInfo: WindowInfo): Float {
    if (widowInfo.screenHeightInfo is WindowInfo.WindowType.Compact ||
        widowInfo.screenWithInfo is WindowInfo.WindowType.Expended
    ) {
        return 12f
    } else {
        return 6f
    }
}