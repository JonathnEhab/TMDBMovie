package com.example.domain.usecase

import com.example.domain.models.MovieDetailsDomainModel
import com.example.domain.repostiory.MovieRepository
import com.example.domain.state.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchMovieDetails @Inject constructor(private val movieRepository: MovieRepository){
    suspend operator fun invoke(movieId: Int): Flow<ResultState<MovieDetailsDomainModel>> = flow {
        emit(movieRepository.fetchMovieDetails(movieId))}
}