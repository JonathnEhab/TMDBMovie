package com.example.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repostiory.MovieRepository
import com.example.domain.state.ResultState
import com.example.state.detailsstate.DetailsIntent
import com.example.state.detailsstate.DetailsState
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
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _detailsState = MutableStateFlow<DetailsState>(DetailsState.Loading)
    val detailsState: StateFlow<DetailsState> = _detailsState.asStateFlow()

    fun handleIntent(intent: DetailsIntent) {
        when (intent) {
            is DetailsIntent.FetchMovieDetails -> fetchMovieDetails(intent.id)
        }
    }

    private fun fetchMovieDetails(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _detailsState.value = DetailsState.Loading
            when (val result = repository.fetchMovieDetails(id)) {
                is ResultState.Error -> {
                    _detailsState.value=  DetailsState.Error(result.message)
                }
                is ResultState.Success -> {
                    _detailsState.value = DetailsState.Success(result.data)
                }
            }
        }
    }
}
