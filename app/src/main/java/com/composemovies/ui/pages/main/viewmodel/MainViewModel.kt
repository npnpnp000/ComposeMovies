package com.composemovies.ui.pages.main.viewmodel


import android.util.Log
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composemovies.data.repositories.Repository
import com.composemovies.model.response_models.Genre
import com.composemovies.model.response_models.Result
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    var genreListNames = mutableStateOf(arrayListOf(""))
    var moviesList = mutableStateOf(arrayListOf<Result>())
    var selectedTab = mutableStateOf(0)
    var genreList = arrayListOf<Genre>()
    var listState: LazyGridState? = null
    var currentPage = 1


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

    fun setListOfMovies(genreId: Int, page: Int = 1) = viewModelScope.launch {
        currentPage = page
        val fixedGenreId =  getGenreIdFromTheList(genreList,genreId)
            when (val result = repository.getListOfMovies(fixedGenreId, currentPage)) {
                is com.movies_selcom.data.error_handler.Result.Error -> {
                    val errorMessage = result.error
                    Log.e("Error", "error: " + errorMessage.toString())
                }

                is com.movies_selcom.data.error_handler.Result.Success -> {
                    result.data?.let { movieModle ->
                        if (currentPage != 1) moviesList.value += movieModle.results
                        else moviesList.value = movieModle.results
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

