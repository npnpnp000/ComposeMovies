package com.composemovies.data.remote.api


import com.composemovies.model.response_models.MoviesModel
import com.composemovies.model.response_models.GenreItems
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MoviesApi {
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2NzQ4OGNmODYzZjJmNTZhNDY2Y2ZjYTY3NmNmZDc4ZCIsIm5iZiI6MTczMjY1NzIxNi40NzU1MTI1LCJzdWIiOiI2MWIzMDY2OTY3ZTBmNzAwODZkMmRmMzkiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.WGX8RkTh9EcttstE_jr44F4lT_Z9NQIYtFc1lUVNEf4")
    @GET("/3/genre/movie/list?language=en")
    suspend fun getGenreMovies(): GenreItems?

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2NzQ4OGNmODYzZjJmNTZhNDY2Y2ZjYTY3NmNmZDc4ZCIsIm5iZiI6MTczMjY1NzIxNi40NzU1MTI1LCJzdWIiOiI2MWIzMDY2OTY3ZTBmNzAwODZkMmRmMzkiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.WGX8RkTh9EcttstE_jr44F4lT_Z9NQIYtFc1lUVNEf4")
    @GET("/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc")
    suspend fun getListOfMovies(@Query("with_genres") genreName: String): MoviesModel?
}
