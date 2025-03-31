package com.example.presentation.viewmodel

import com.example.domain.models.MovieDomainModel
import com.example.domain.repostiory.MovieRepository
import com.example.domain.state.ResultState
import com.example.presentation.viewmodels.MoviesViewModel
import com.example.state.moviesstate.MoviesIntent
import com.example.state.moviesstate.MoviesState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesViewModelTest {

    private lateinit var viewModel: MoviesViewModel
    private val repository: MovieRepository = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MoviesViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when fetch movies is successful, state should be Success`() = runTest {
        // Given
        val mockMovies = listOf(
            MovieDomainModel(id = 1, title = "Test Movie", posterPath = "sa", releaseDate = "sk", voteAverage = 2.2)
        )
        coEvery { repository.fetchNowPlayingMovies() } returns ResultState.Success(mockMovies)

        // When
        viewModel.handleIntent(MoviesIntent.FetchNowPlayingMovies)
        advanceUntilIdle() // تشغيل جميع الكوروتينات النشطة

        // Then
        val state = viewModel.moviesState.first()
        assertEquals(MoviesState.Success(mockMovies), state)
    }
}
