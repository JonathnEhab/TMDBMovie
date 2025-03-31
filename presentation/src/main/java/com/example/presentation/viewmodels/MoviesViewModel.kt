package com.example.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repostiory.MovieRepository
import com.example.domain.state.ResultState
import com.example.state.moviesstate.MoviesIntent
import com.example.state.moviesstate.MoviesState
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

    private val _moviesState = MutableStateFlow<MoviesState>(MoviesState.Loading)
    val moviesState: StateFlow<MoviesState> = _moviesState.asStateFlow()

    fun handleIntent(intent: MoviesIntent) {
        when (intent) {
            is MoviesIntent.FetchNowPlayingMovies -> fetchNowPlayingMovies()
        }
    }

    private fun fetchNowPlayingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _moviesState.value = MoviesState.Loading
            when (val result = repository.fetchNowPlayingMovies()) {
                is ResultState.Success -> {
                    _moviesState.value = MoviesState.Success(result.data)
                }
                is ResultState.Error -> {
                    _moviesState.value = MoviesState.Error(result.message)
                }
            }
        }
    }
}


