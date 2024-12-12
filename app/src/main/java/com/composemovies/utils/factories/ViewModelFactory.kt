package com.composemovies.utils.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.composemovies.data.repositories.Repository
import com.composemovies.ui.pages.main.viewmodel.MainViewModel


@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val repository: Repository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when (modelClass) {
            MainViewModel::class.java -> MainViewModel(repository) as T
            else -> throw Exception("ViewModel not found")
        }
}