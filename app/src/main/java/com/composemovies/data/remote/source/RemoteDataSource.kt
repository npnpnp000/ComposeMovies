package com.composemovies.data.remote.source

import com.composemovies.data.remote.api.MoviesApi
import com.composemovies.model.response_models.Genre
import com.composemovies.model.response_models.GenreItems
import com.composemovies.model.response_models.MoviesModel
import com.composemovies.utils.extensions.findErrorGenre
import com.composemovies.utils.extensions.findErrorMovie
import com.movies_selcom.data.error_handler.DataError
import com.movies_selcom.data.error_handler.Result
import org.json.JSONObject
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



    suspend fun getListOfMovies(genreName: String):Result<MoviesModel?, DataError.Network> {
        return try {
            Result.Success(moviesApi.getListOfMovies(genreName))
        } catch (e: HttpException) {
            return findErrorMovie(e)
        }catch ( e :Exception){
            Result.Error(DataError.Network.Unknown)
        }
    }

    fun <T> retrofitErrorHandler(res: Response<T>): T {
        if (res.isSuccessful) {
            return res.body()!!
        } else {
            val errMsg = res.errorBody()?.string()?.let {
                JSONObject(it).getString("error") // or whatever your message is
            } ?: run {
                res.code().toString()
            }

            throw Exception(errMsg)
        }
    }
}