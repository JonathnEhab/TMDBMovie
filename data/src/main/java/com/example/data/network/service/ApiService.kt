package com.example.data.network.service


import com.example.data.network.modelnetwork.MovieDbResultDataModel
import com.example.data.network.modelnetwork.MovieDetailsDataModel
import com.example.util.APIEndPoint
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(APIEndPoint.GET_NOW_PLAYING_MOVIES_ENDPOINT)
    suspend fun fetchNowPlayingMovies():Response<MovieDbResultDataModel>

    @GET(APIEndPoint.GET_UPCOMING_MOVIES_ENDPOINT)
    suspend fun fetchUpcomingMovies( @Query(APIEndPoint.PAGE_PARAM) page: Int) :Response<MovieDbResultDataModel>


    @GET(APIEndPoint.GET_MOVIE_DETAILS_ENDPOINT)
    suspend fun fetchMovieDetails(
        @Path(APIEndPoint.MOVIE_ID_PARAM) movieId: Int,
    ):Response<MovieDetailsDataModel>
}