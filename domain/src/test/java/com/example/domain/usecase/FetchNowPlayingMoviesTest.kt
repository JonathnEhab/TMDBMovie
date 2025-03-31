package com.example.domain.usecase

import com.example.domain.models.MovieDomainModel
import com.example.domain.repostiory.MovieRepository
import com.example.domain.state.ResultState
import com.example.domain.usecase.FetchNowPlayingMovies
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FetchNowPlayingMoviesTest {

    private lateinit var fetchNowPlayingMovies: FetchNowPlayingMovies
    private val movieRepository: MovieRepository = mockk()

    @Before
    fun setUp() {
        fetchNowPlayingMovies = FetchNowPlayingMovies(movieRepository)
    }

    @Test
    fun `invoke should return list of movies when repository returns success`() = runBlocking {

        val fakeMovies = listOf(
            MovieDomainModel(id = 1, title = "Cleaner", posterPath = "https://api.themoviedb.org/mwzDApMZAGeYCEVjhegKvCzDX0W.jpg", releaseDate = "2024-01-01", voteAverage = 6.593),
            MovieDomainModel(id = 2, title = "The Codes of War", posterPath = "/oXeiQAfRK90pxxhP5iKPXQqAIN1.jpg", releaseDate = "2024-02-01", voteAverage = 7.3)
        )
        val expectedResult = ResultState.Success(fakeMovies)


        coEvery { movieRepository.fetchNowPlayingMovies() } returns expectedResult


        val result = fetchNowPlayingMovies().first()
        assertEquals(expectedResult, result)

        coVerify(exactly = 1) { movieRepository.fetchNowPlayingMovies() }
    }
}
