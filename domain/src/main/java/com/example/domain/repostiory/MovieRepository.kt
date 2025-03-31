package com.example.domain.repostiory



import com.example.domain.models.MovieDetailsDomainModel
import com.example.domain.models.MovieDomainModel
import com.example.domain.state.ResultState

interface MovieRepository {
    suspend fun fetchNowPlayingMovies(): ResultState<List<MovieDomainModel>>
    suspend fun fetchMovieDetails(movieId: Int): ResultState<MovieDetailsDomainModel>
}