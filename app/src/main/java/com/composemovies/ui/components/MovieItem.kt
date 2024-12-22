package com.composemovies.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composemovies.model.response_models.Result

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieItem(result: Result) {
    Card(modifier = Modifier.padding(5.dp).background(color = MaterialTheme.colorScheme.background),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Row(Modifier.weight(0.6f)) {  Image(result) }
            FlowRow(Modifier.weight(0.1f)) {StarRating(result)}
            Row(Modifier.weight(0.3f)) {TextItem(result)}

        }
    }

}


