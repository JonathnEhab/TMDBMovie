package com.example.presentation.viewmodel

import com.bumptech.glide.load.engine.Resource
import com.example.domain.models.MovieDomainModel
import com.example.domain.repostiory.MovieRepository
import com.example.domain.state.ResultState
import com.example.presentation.viewmodels.MoviesViewModel
import com.example.state.moviesstate.MoviesIntent
import com.example.state.moviesstate.MoviesState
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class MoviesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private lateinit var viewModel: MoviesViewModel
    private val repository: MovieRepository = mockk()

    @Before
    fun setup() {
        viewModel = MoviesViewModel(repository)
    }
    @Test
    fun `fetchNowPlayingMovies should update state to Success when repository returns data`() = runTest {

        val movies = listOf(MovieDomainModel(id = 1, title = "Movie 1", releaseDate = "sdmasl", posterPath = "sm", voteAverage = 4.5))
        coEvery { repository.fetchNowPlayingMovies() } returns ResultState.Success(movies)

        viewModel.handleIntent(MoviesIntent.FetchNowPlayingMovies)
        advanceUntilIdle()
        assert(viewModel.moviesState.value is MoviesState.Success)
        assertEquals(movies, (viewModel.moviesState.value as MoviesState.Success).movies)
    }
}




