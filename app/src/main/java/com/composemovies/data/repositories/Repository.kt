package com.composemovies.data.repositories

import com.composemovies.data.remote.source.RemoteDataSource


class Repository(
    private val remoteDataSource: RemoteDataSource,
) {

    suspend fun getGenreMovies() = remoteDataSource.getGenreMovies()

    suspend fun getListOfMovies(genreName: String, page: Int) = remoteDataSource.getListOfMovies(genreName, page)

}