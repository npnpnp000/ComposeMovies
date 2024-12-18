package com.composemovies.ui.pages.main.viewmodel


import android.util.Log
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composemovies.data.error_handler.DataError
import com.composemovies.data.repositories.Repository
import com.composemovies.model.response_models.Genre
import com.composemovies.model.response_models.GenreItems
import com.composemovies.model.response_models.MoviesModel
import com.composemovies.model.response_models.Result
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    var genreListNames = mutableStateOf(arrayListOf(""))
    var moviesList =  mutableStateOf(arrayListOf<Result>())
    var selectedTab = mutableIntStateOf(0)
    var genreList = arrayListOf<Genre>()
    var listState: LazyGridState? = null
    var currentPage = 1
    var maxPages = 1


    init {
        getListOfGenre()
    }

    private fun getListOfGenre() = viewModelScope.launch {
        checkGenreErrors(repository.getGenreMovies()){ genreItems ->
            setGenresData(genreItems)
        }
    }

    private fun setGenresData(genreItems: GenreItems) {

        genreList = genreItems.genres

        genreListNames.value = getNameList(genreList)

    }

    private fun getNameList(list: List<Genre>): ArrayList<String> {

        val returnList = arrayListOf<String>()
        list.map {
            returnList.add(it.name)
        }
        return returnList
    }

    fun setListOfMovies(genreId: Int, page: Int = 1) = viewModelScope.launch {
        if (page <= maxPages){
            currentPage = page
            val fixedGenreId =  getGenreIdFromTheList(genreList,genreId)

            checkMoviesErrors(repository.getListOfMovies(fixedGenreId, currentPage)){ movieModule ->
                setListMoviesData(movieModule)
            }
        }
    }

    private fun setListMoviesData(movieModule: MoviesModel) {
        Log.e("moviesList","setListOfMovies page: $currentPage")

        if (currentPage != 1) moviesList.value += movieModule.results
        else moviesList.value = movieModule.results

        maxPages = movieModule.total_pages
    }

    private fun getGenreIdFromTheList(genreList: ArrayList<Genre>, genreId: Int): String {
        var fixedGenreId = "28" // default is 28 - Action

        if (genreList.isNotEmpty()){fixedGenreId = genreList[genreId].id.toString()}

        return fixedGenreId
    }

    private fun checkMoviesErrors(result: com.composemovies.data.error_handler. Result<MoviesModel?, DataError. Network>, setData: (MoviesModel)-> Unit){
        when (result) {
            is com.composemovies.data.error_handler.Result.Error -> {
                val errorMessage = result.error
                Log.e("Error", "error: " + errorMessage.toString())
            }

            is com.composemovies.data.error_handler.Result.Success -> {
                result.data?.let { movieModule ->
                    setData(movieModule)
                }
            }
        }
    }
    private fun checkGenreErrors(result: com.composemovies.data.error_handler. Result<GenreItems?, DataError. Network>, setData: (GenreItems)-> Unit){
        when (result) {
            is com.composemovies.data.error_handler.Result.Error -> {
                val errorMessage = result.error
                Log.e("Error", "error: " + errorMessage.toString())
            }

            is com.composemovies.data.error_handler.Result.Success -> {
                result.data?.let { genreItems ->
                    setData(genreItems)
                }
            }
        }
    }
}

