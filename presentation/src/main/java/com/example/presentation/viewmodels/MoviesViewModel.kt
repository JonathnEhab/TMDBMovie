package com.example.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repostiory.MovieRepository
import com.example.domain.state.ResultState
import com.example.state.moviesstate.MoviesIntent
import com.example.state.moviesstate.MoviesState
import com.example.util.ExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _nowPlayingMoviesState = MutableStateFlow<MoviesState>(MoviesState.Loading)
    val nowPlayingMoviesState: StateFlow<MoviesState> = _nowPlayingMoviesState.asStateFlow()


    fun handleIntent(intent: MoviesIntent) {
        when (intent) {
            is MoviesIntent.FetchNowPlayingMovies -> fetchNowPlayingMovies()
        }
    }

    private fun fetchNowPlayingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _nowPlayingMoviesState.value = MoviesState.Loading
            when (val result = repository.fetchNowPlayingMovies()) {
                is ResultState.Success -> {
                    _nowPlayingMoviesState.value = MoviesState.Success(result.data)
                    Log.d("MoviesViewModel", "fetchNowPlayingMovies: ${result.data.size} ")
                }
                is ResultState.Error -> {
                    _nowPlayingMoviesState.value = MoviesState.Error(ExceptionHandler.handleException(Exception(result.message)))
                    Log.d("MoviesViewModel", "fetchNowPlayingMovies: ${result.message} ")
                }
            }
        }
    }


}