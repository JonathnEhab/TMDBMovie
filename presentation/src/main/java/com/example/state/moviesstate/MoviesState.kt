package com.example.state.moviesstate

import com.example.domain.models.MovieDomainModel

sealed class MoviesState {
        data object Loading : MoviesState()
        data class Success(val movies: List<MovieDomainModel>) : MoviesState()
        data class Error(val message: String) : MoviesState()


}