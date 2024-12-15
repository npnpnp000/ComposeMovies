package com.composemovies.data.remote.source

import com.composemovies.data.remote.api.MoviesApi
import com.composemovies.model.response_models.GenreItems
import com.composemovies.model.response_models.MoviesModel
import com.composemovies.utils.extensions.findErrorGenre
import com.composemovies.utils.extensions.findErrorMovie
import com.movies_selcom.data.error_handler.DataError
import com.movies_selcom.data.error_handler.Result
import retrofit2.*

class RemoteDataSource(private val moviesApi: MoviesApi) {

    suspend fun getGenreMovies():  Result<GenreItems?, DataError.Network>{

        return try {
            Result.Success(moviesApi.getGenreMovies())
        } catch (e: HttpException) {
           return findErrorGenre(e)
        }catch ( e :Exception){
            Result.Error(DataError.Network.Unknown)
        }
    }



    suspend fun getListOfMovies(genreName: String, page: Int):Result<MoviesModel?, DataError.Network> {
        return try {
            Result.Success(moviesApi.getListOfMovies(genreName, page))
        } catch (e: HttpException) {
            return findErrorMovie(e)
        }catch ( e :Exception){
            Result.Error(DataError.Network.Unknown)
        }
    }

}