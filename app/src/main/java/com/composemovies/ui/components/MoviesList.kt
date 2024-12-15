package com.composemovies.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.composemovies.ui.pages.main.viewmodel.MainViewModel

@Composable
fun MoviesList(mainViewModel: MainViewModel) {
    val movies = mainViewModel.moviesList
    mainViewModel.listState = rememberLazyGridState()
    LazyHorizontalGrid(state = mainViewModel.listState!!, rows = GridCells.Fixed(2),
        verticalArrangement = Arrangement.SpaceEvenly) {
        items(movies.value.size) { i ->
            MovieItem(movies.value[i])
        }
    }

    mainViewModel.listState?.let { listState->

        val reachedBottom by remember {
            derivedStateOf {
                val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
                lastVisibleItem?.index != 0 && lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - 1
            }
        }
        // act when end of list reached
        LaunchedEffect(reachedBottom) {
            if (reachedBottom) {
                mainViewModel.setListOfMovies(
                    mainViewModel.selectedTab.value,
                    mainViewModel.currentPage + 1
                )
                Log.e("testimage", "End the list")
            }
        }
    }
}