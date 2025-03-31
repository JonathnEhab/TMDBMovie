package com.example.data.network.remotesource

import com.example.data.network.modelnetwork.MovieDbResultDataModel
import com.example.data.network.modelnetwork.MovieDetailsDataModel
import com.example.data.network.service.ApiService
import com.example.data.network.state.DataState
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) : RemoteDataSource {

    override suspend fun fetchNowPlayingMovies(

    ): DataState<MovieDbResultDataModel> {
        return try {
            val response = apiService.fetchNowPlayingMovies()
            if (response.isSuccessful && response.body() != null) {
                DataState.Success(response.body()!!)
            } else {
                DataState.Error(response.message() ?: "Unknown error occurred")
            }
        } catch (e: Exception) {
            DataState.Error(e.localizedMessage ?: "An error occurred")
        }
    }

    override suspend fun fetchMovieDetails(movieId: Int): DataState<MovieDetailsDataModel> {
        return try {
            val response = apiService.fetchMovieDetails(movieId)
            if (response.isSuccessful && response.body() != null) {
                DataState.Success(response.body()!!)
            } else {
                DataState.Error(response.message() ?: "Unknown error occurred")
            }
        } catch (e: Exception) {
            DataState.Error(e.localizedMessage ?: "An error occurred")
        }
    }
}
