package com.example.presentation.viewmodel

import app.cash.turbine.test
import com.example.domain.models.MovieDetailsDomainModel
import com.example.domain.repostiory.MovieRepository
import com.example.domain.state.ResultState
import com.example.presentation.viewmodels.MovieDetailsViewModel
import com.example.state.detailsstate.DetailsIntent
import com.example.state.detailsstate.DetailsState
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
class MovieDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: MovieDetailsViewModel
    private val repository: MovieRepository = mockk()

    @Before
    fun setup() {
        viewModel = MovieDetailsViewModel(repository)
    }

    @Test
    fun `fetchMovieDetails should emit Loading then Success when repository returns data`() = runTest {
        // Arrange
        val movieDetails = MovieDetailsDomainModel(
            id = 1,
            tagline = "The Best Movie",
            title = "Test Movie",
            voteAverage = 8.5,
            voteCount = 1000,
            releaseDate = "2024-01-01",
            popularity = 99.9,
            runtime = 120,
            overview = "This is a test movie.",
            budget = 5000000,
            posterPath = "/test.jpg"
        )

        coEvery { repository.fetchMovieDetails(1) } returns ResultState.Success(movieDetails)

        viewModel.detailsState.test {
            // Trigger intent before checking state emissions
            viewModel.handleIntent(DetailsIntent.FetchMovieDetails(1))

            // First emission should be Loading
            assertEquals(DetailsState.Loading, awaitItem())

            // Advance until all coroutines complete
            advanceUntilIdle()

            // Success state should be emitted after Loading
            val successState = awaitItem() as DetailsState.Success
            assertEquals(movieDetails, successState.movies)

            expectNoEvents()
        }
    }


    @Test
    fun `initial state should be Loading`() = runTest {
        viewModel.detailsState.test {
            assertEquals(DetailsState.Loading, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}

