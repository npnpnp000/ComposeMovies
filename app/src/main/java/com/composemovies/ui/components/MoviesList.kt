package com.composemovies.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
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


    val state = rememberLazyGridState()
    mainViewModel.listState = state

    LazyHorizontalGrid(state = state, rows = GridCells.Fixed(2),
        verticalArrangement = Arrangement.SpaceEvenly) {
        items(mainViewModel.moviesList.value.size) { i ->
            MovieItem(mainViewModel.moviesList.value[i])
        }
    }
        // act when end of list reached
        LaunchedEffect(state.isScrollInProgress) {
            if (mainViewModel.moviesList.value.isNotEmpty()) {
                Log.e("testimage", "last: ${state.layoutInfo.visibleItemsInfo.last().index}")

                if (state.layoutInfo.visibleItemsInfo.last().index == mainViewModel.moviesList.value.size-1) {
                    mainViewModel.setListOfMovies(
                        mainViewModel.selectedTab.intValue,
                        mainViewModel.currentPage + 1
                    )
                    Log.e("testimage", "End the list")
                }
            }
        }
}


@Composable
private  fun  getState(state: LazyGridState): Boolean {
       val isState :Boolean by remember {
           derivedStateOf {
               val lastVisibleItem = state.layoutInfo.visibleItemsInfo.lastOrNull()

               lastVisibleItem?.index != 0 && lastVisibleItem?.index == state.layoutInfo.totalItemsCount - 1
           }
       }
    return isState
}
