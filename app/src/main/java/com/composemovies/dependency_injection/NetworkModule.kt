package com.movies_selcom.dependency_injection

import com.composemovies.data.remote.api.MoviesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NetworkModule {

    fun provideMessagesApi(): MoviesApi {
        return provideRetrofitClient().create(MoviesApi::class.java)
    }

    private fun provideRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https:///api.themoviedb.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}