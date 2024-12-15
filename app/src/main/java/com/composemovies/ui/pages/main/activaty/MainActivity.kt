package com.composemovies.ui.pages.main.activaty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.composemovies.ui.components.MainTitle
import com.composemovies.ui.components.MoviesList
import com.composemovies.ui.components.TopTabs
import com.composemovies.ui.pages.main.viewmodel.MainViewModel
import com.composemovies.ui.theme.ComposeMoviesTheme
import com.composemovies.utils.extensions.provideViewModel


class MainActivity : ComponentActivity() {

    val mainViewModel: MainViewModel by provideViewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeMoviesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(modifier = Modifier.fillMaxSize(),
                        topBar = {
                            CenterAlignedTopAppBar(colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                                title= {
                                    MainTitle()
                                }
                            )
                        }) { innerPadding ->
                        Main(modifier = Modifier.padding(innerPadding))
                    }
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
            TopTabs(mainViewModel)
            MoviesList(mainViewModel)
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