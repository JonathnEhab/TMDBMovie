package com.example.domain.usecase
import com.example.domain.models.MovieDetailsDomainModel
import com.example.domain.repostiory.MovieRepository
import com.example.domain.state.ResultState
import com.example.domain.usecase.FetchMovieDetails
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class FetchMovieDetailsTest {

    private lateinit var repository: MovieRepository
    private lateinit var fetchMovieDetails: FetchMovieDetails

    @Before
    fun setUp() {
        repository = mock(MovieRepository::class.java)
        fetchMovieDetails = FetchMovieDetails(repository)
    }

    @Test
    fun `invoke should return movie details from repository`() = runTest {

        val movieId = 1
        val fakeMovieDetails = MovieDetailsDomainModel(
            id = movieId,
            title = "Fake Movie",
            overview = "Fake Overview",
            budget = 1000000,
            popularity = 7.8,
            posterPath = "/fake_poster.jpg",
            releaseDate = "2024-03-30",
            runtime = 120,
            tagline = "Fake Tagline",
            voteAverage = 8.5,
            voteCount = 2000
        )

        val expectedResult = ResultState.Success(fakeMovieDetails)


        `when`(repository.fetchMovieDetails(movieId)).thenReturn(expectedResult)


        val result = fetchMovieDetails(movieId).first()


        assertEquals(expectedResult, result)
    }
}
