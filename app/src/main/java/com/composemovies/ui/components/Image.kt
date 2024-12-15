package com.composemovies.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.composemovies.R
import com.composemovies.model.response_models.Result

@Composable
fun Image(result: Result){
    AsyncImage(
        alignment = Alignment.Center,
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://image.tmdb.org/t/p/w780${result.poster_path}")
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.ic_launcher_background),
        error = painterResource(R.drawable.image_load_failed_128),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier.fillMaxSize()
            .padding(5.dp)
    )
}