package com.composemovies.ui.pages.main.activaty

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.composemovies.R
import com.composemovies.model.response_models.Result
import com.composemovies.ui.components.AutoResizedText
import com.composemovies.ui.pages.main.viewmodel.MainViewModel
import com.composemovies.ui.theme.ComposeMoviesTheme
import com.composemovies.utils.extensions.WindowInfo
import com.composemovies.utils.extensions.provideViewModel
import com.composemovies.utils.extensions.rememberWidowInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
                    onClick = { onSelectedTab(selectedTabIndex,index, coroutineScope) },
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
        coroutineScope: CoroutineScope
    ) {
        selectedTabIndex.value = index

        coroutineScope.launch {
            if(mainViewModel.moviesList.value.isNotEmpty()){
                mainViewModel.listState?.scrollToItem(0)
            }
        }

    }

    @Composable
fun MoviesList() {
    val movies = mainViewModel.moviesList
    mainViewModel.listState = rememberLazyGridState()
    LazyHorizontalGrid(state = mainViewModel.listState!!, rows = GridCells.Fixed(2),
        verticalArrangement = Arrangement.SpaceEvenly) {
        items(movies.value.size) { i ->
            MovieItem(movies.value[i])
        }
    }

        mainViewModel.listState?.let { listState->

            val reachedBottom: Boolean by remember {
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



    @Composable
fun MovieItem(result: Result) {
    Log.e("testimage","https://image.tmdb.org/t/p/w500${result.poster_path}")
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
            modifier = Modifier
                .padding(5.dp)
                .weight(0.6f)
        )

        Box(Modifier.weight(0.1f),
            contentAlignment = Alignment.Center) {
            StarRatingSample(result.vote_average)
        }
        val widowInfo = rememberWidowInfo()
        val modifier = getModifierByScreenInfo(widowInfo)
        val style = getStyleByScreenInfo(widowInfo)
        AutoResizedText(
            text = setMovieText(result),
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = modifier.weight(0.3f),
            style = style

        )
    }
}
    @Composable
    private fun getStyleByScreenInfo(widowInfo: WindowInfo): TextStyle {
        if (widowInfo.screenHeightInfo is WindowInfo.WindowType.Compact ||
            widowInfo.screenWithInfo is WindowInfo.WindowType.Expended){
            return MaterialTheme.typography.bodyLarge
        }else{
            return MaterialTheme.typography.bodySmall
        }
    }
    private fun getModifierByScreenInfo(widowInfo: WindowInfo):Modifier{
        if (widowInfo.screenHeightInfo is WindowInfo.WindowType.Compact ||
            widowInfo.screenWithInfo is WindowInfo.WindowType.Expended){
            return Modifier
                .padding(5.dp)
                .width(200.dp)
        }else{
            return Modifier
                .padding(5.dp)
                .width(100.dp)
        }
    }

    private fun setMovieText(result: Result): String { //
        return result.title +
                "\n" +
                " (${getYearOnly(result.release_date)})"
    }

    private fun getYearOnly(releaseDate: String): String {
        return releaseDate.split("-")[0] // get the first from YYYY-MM-DD date format
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

    private fun getStarSizeByScreenInfo(widowInfo: WindowInfo): Float{
        if (widowInfo.screenHeightInfo is WindowInfo.WindowType.Compact ||
            widowInfo.screenWithInfo is WindowInfo.WindowType.Expended){
            return 12f
        }else{
            return 6f
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