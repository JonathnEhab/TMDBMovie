package com.example.state.moviesstate

sealed class MoviesIntent {
    data object FetchNowPlayingMovies : MoviesIntent()
}