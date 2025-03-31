package com.example.domain.usecase

import com.example.domain.models.MovieDetailsDomainModel
import com.example.domain.models.MovieDomainModel
import com.example.domain.repostiory.MovieRepository
import com.example.domain.state.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchNowPlayingMovies @Inject constructor(private val movieRepository: MovieRepository) {
   suspend operator fun invoke(): Flow<ResultState<List<MovieDomainModel>>> = flow {
        emit(movieRepository.fetchNowPlayingMovies())
    }
}