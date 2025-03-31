package com.example.state.detailsstate


import com.example.domain.models.MovieDetailsDomainModel


sealed class DetailsState {
    data object Loading : DetailsState()
    data class Success(val movies: MovieDetailsDomainModel) : DetailsState()
    data class Error(val message: String, val exception: Throwable? = null) : DetailsState()
}