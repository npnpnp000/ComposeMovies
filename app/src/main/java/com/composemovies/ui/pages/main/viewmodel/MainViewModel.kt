package com.composemovies.ui.pages.main.viewmodel


import android.util.Log
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composemovies.data.repositories.Repository
import com.composemovies.model.response_models.Genre
import com.composemovies.model.response_models.Result
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    var genreListNames = mutableStateOf(arrayListOf<String>("item1", "item2", "item3"))
    var moviesList = mutableStateOf(arrayListOf<Result>())
    var selectedTab = mutableStateOf(0)
    var genreList = arrayListOf<Genre>()

    init {
        getListOfGenre()
    }

    private fun getListOfGenre() = viewModelScope.launch {

        when (val result = repository.getGenreMovies()) {
            is com.movies_selcom.data.error_handler.Result.Error -> {
                val errorMessage = result.error
                Log.e("Error", "error: " + errorMessage.toString())
            }

            is com.movies_selcom.data.error_handler.Result.Success -> {
                Log.e("test", "resolt:" + result.data.toString())
                result.data?.let {
                    genreList = it.genres
                    genreListNames.value = getNameList(genreList)
                }
            }
        }
    }

    private fun getNameList(list: List<Genre>): ArrayList<String> {

        val returnList = arrayListOf<String>()
        list.map {
            returnList.add(it.name)
        }
        return returnList
    }

    fun setGenre(genreId: Int) = viewModelScope.launch {
        Log.e("test", "genreName:$genreId")
        val fixedGenreId =  getGenreIdFromTheList(genreList,genreId)
            when (val result = repository.getListOfMovies(fixedGenreId)) {
                is com.movies_selcom.data.error_handler.Result.Error -> {
                    val errorMessage = result.error
                    Log.e("Error", "error: " + errorMessage.toString())
                }

                is com.movies_selcom.data.error_handler.Result.Success -> {
                    Log.e("test", "resolt:" + result.data.toString())
                    result.data?.let { movieModle ->
                        moviesList.value = movieModle.results
                }

            }
        }
    }

    private fun getGenreIdFromTheList(genreList: ArrayList<Genre>, genreId: Int): String {
        var fixedGenreId = "28" // default is 28 - Action

        if (genreList.isNotEmpty()){fixedGenreId = genreList[genreId].id.toString()}

        return fixedGenreId
    }
}

