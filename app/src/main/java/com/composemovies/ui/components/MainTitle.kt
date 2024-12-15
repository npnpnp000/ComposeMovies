package com.composemovies.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainTitle() {

    Text(
        text = "Movies",
        fontSize = 30.sp,
        color = Color.Black,
        modifier = Modifier.padding(5.dp)
    )
}