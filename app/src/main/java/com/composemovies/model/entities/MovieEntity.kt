package com.composemovies.model.entities


data class MovieEntity(
    val name: String,
    val id: Int = -1,
    val image_path: String,
    val overview: String
)
