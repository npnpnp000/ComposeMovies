package com.movies_selcom.dependency_injection

import com.composemovies.data.repositories.Repository


object RepositoryModule {

    fun provideRepository(): Repository {
        return Repository(
            DataSourceModule.provideRemoteDataSource()
        )
    }
}