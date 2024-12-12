package com.composemovies.dependency_injection

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.composemovies.ui.pages.main.viewmodel.MainViewModel
import com.composemovies.utils.factories.ViewModelFactory
import com.movies_selcom.dependency_injection.RepositoryModule


object ViewModelModule {

    inline fun <reified VM : ViewModel> provideViewModel(activity: ComponentActivity): Lazy<VM> {
        val viewModelFactory = when (VM::class.java) {
            MainViewModel::class.java -> {
                ViewModelFactory(RepositoryModule.provideRepository())
            }

            else -> throw RuntimeException("ViewModel does not exist")
        }
        return lazy { ViewModelProvider(activity, viewModelFactory)[VM::class.java] }
    }
}