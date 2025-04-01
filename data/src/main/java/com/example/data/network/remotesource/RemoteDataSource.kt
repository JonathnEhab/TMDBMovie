package com.example.data.network.remotesource

import com.example.data.network.modelnetwork.MovieDbResultDataModel
import com.example.data.network.modelnetwork.MovieDetailsDataModel
import com.example.data.network.state.DataState

interface RemoteDataSource {
    suspend fun fetchNowPlayingMovies(): DataState<MovieDbResultDataModel>
    suspend fun fetchUpcomingMovies(page: Int):DataState<MovieDbResultDataModel>
    suspend fun fetchMovieDetails(movieId: Int): DataState<MovieDetailsDataModel>
}