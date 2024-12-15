package com.composemovies.dependency_injection

import com.composemovies.data.remote.source.RemoteDataSource


object DataSourceModule {


    fun provideRemoteDataSource() : RemoteDataSource {
        return RemoteDataSource(NetworkModule.provideMessagesApi())
    }

}