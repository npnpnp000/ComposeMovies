package com.composemovies.ui.pages.main.activaty

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.composemovies.R
import com.composemovies.model.response_models.Result
import com.composemovies.ui.pages.main.viewmodel.MainViewModel
import com.composemovies.ui.theme.ComposeMoviesTheme
import com.composemovies.utils.extensions.provideViewModel

class MainActivity : ComponentActivity() {

    val mainViewModel: MainViewModel by provideViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeMoviesTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//
                    Main( modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

@Composable
fun Main(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MainTitle()
        TopTabs()
        MoviesList()
    }
}

@Composable
fun TopTabs() {
    TabView()
}


@Composable
fun MainTitle() {

    Text(
        text = "Movies",
        fontSize = 30.sp,
        color = Color.Black,
        modifier = Modifier.padding(5.dp)
    )

}

@Composable
fun TabView() {
    val selectedTabIndex = mainViewModel.selectedTab
     val tabs = mainViewModel.genreListNames
    Column {

        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex.value,
            edgePadding = 16.dp,
            contentColor = Color.Gray,
            containerColor = Color.White,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex.value.toInt()])
                        .fillMaxWidth(),
                    color = Color.Black
                )
            }
        ) {
            tabs.value.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    onClick = { selectedTabIndex.value = index },
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

        mainViewModel.setGenre(selectedTabIndex.value)
    }
}

@Composable
fun MoviesList() {
    val movies = mainViewModel.moviesList.value
    LazyHorizontalGrid(rows = GridCells.Fixed(2),
        verticalArrangement = Arrangement.Center) {
        items(movies.size) { i ->
            MovieItem(movies[i])
        }
    }
}

@Composable
fun MovieItem(result: Result) {
    Log.e("testimage","https://image.tmdb.org/t/p/w500${result.poster_path}")
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://image.tmdb.org/t/p/w780${result.poster_path}")
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_launcher_background),
            error = painterResource(R.drawable.image_load_failed_128),
            contentDescription = null,
            modifier = Modifier
                .padding(5.dp)
                .weight(1f, fill = true)
        )

        StarRatingSample()

        Text(
            text = result.title,
            color = Color.Black,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
fun StarRatingSample() {
    var rating by remember { mutableFloatStateOf(1f) } //default rating will be 1

    StarRatingBar(
        maxStars = 5,
        rating = rating,
        onRatingChanged = {
            rating = it
        }
    )
}

@Composable
fun StarRatingBar(
    maxStars: Int = 5,
    rating: Float,
    onRatingChanged: (Float) -> Unit
) {
    val density = LocalDensity.current.density
    val starSize = (12f * density).dp
    val starSpacing = (0.5f * density).dp

    Row(
        modifier = Modifier.selectableGroup(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val icon = if (isSelected) Icons.Filled.Star else Icons.Default.Star
            val iconTintColor = if (isSelected) Color(0xFFFFC700) else Color(0x20FFFFFF)
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTintColor,
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

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    ComposeMoviesTheme {
        Main(Modifier.padding())
    }
}

}