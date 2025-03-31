package com.example.state.detailsstate

sealed class DetailsIntent {
    data class FetchMovieDetails(val id:Int):DetailsIntent()
}