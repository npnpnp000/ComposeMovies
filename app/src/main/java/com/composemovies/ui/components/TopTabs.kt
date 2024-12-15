package com.composemovies.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composemovies.ui.pages.main.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TopTabs(mainViewModel: MainViewModel) {
    TabView(mainViewModel)
}

@Composable
fun TabView(mainViewModel: MainViewModel) {
    val selectedTabIndex = mainViewModel.selectedTab
    val tabs = mainViewModel.genreListNames
    val coroutineScope = rememberCoroutineScope()
    Column {

        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex.value,
            edgePadding = 16.dp,
            contentColor = Color.Gray,
            containerColor = Color.White,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex.value])
                        .fillMaxWidth(),
                    color = Color.Black
                )
            }
        ) {
            tabs.value.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    onClick = { onSelectedTab(selectedTabIndex,index, coroutineScope,mainViewModel) },
                    modifier = Modifier.padding(8.dp),
                    content = {
                        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = tab,
                                modifier = Modifier
                                    .padding(8.dp),
                                color = if (selectedTabIndex.value == index) Color.Black else Color.Gray
                            )
                        }
                    }
                )
            }
        }

        mainViewModel.setListOfMovies(selectedTabIndex.value)
    }
}

private fun onSelectedTab(
    selectedTabIndex: MutableState<Int>,
    index: Int,
    coroutineScope: CoroutineScope,
    mainViewModel: MainViewModel
) {
    selectedTabIndex.value = index

    coroutineScope.launch {
        if(mainViewModel.moviesList.value.isNotEmpty()){
            mainViewModel.listState?.scrollToItem(0)
        }
    }

}
