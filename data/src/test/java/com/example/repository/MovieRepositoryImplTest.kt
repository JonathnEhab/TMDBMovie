package com.example.repository

import com.example.data.local.localmodel.MovieEntity
import com.example.data.local.localmodel.toEntity
import com.example.data.local.localsource.LocalDataSource
import com.example.data.network.modelnetwork.MovieDataModel
import com.example.data.network.modelnetwork.MovieDbResultDataModel
import com.example.data.network.modelnetwork.MovieDetailsDataModel
import com.example.data.network.remotesource.RemoteDataSource
import com.example.data.network.state.DataState
import com.example.data.repository.MovieRepositoryImpl
import com.example.domain.state.ResultState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class MovieRepositoryImplTest {


    private val remoteDataSource: RemoteDataSource = mock()
    private val localDataSource: LocalDataSource = mock()


    private lateinit var repository: MovieRepositoryImpl

    @Before
    fun setUp() {

        repository = MovieRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `fetchNowPlayingMovies returns local movies when available`() = runBlockingTest {

        val localMovies = listOf(MovieEntity(1, "Movie 1", "poster.jpg", "2023-10-10", 8.5))
        `when`(localDataSource.getMovies()).thenReturn(flowOf(localMovies))


        val result = repository.fetchNowPlayingMovies()

        assertTrue(result is ResultState.Success)
        assertEquals(localMovies.size, (result as ResultState.Success).data.size)
    }

    @Test
    fun `fetchNowPlayingMovies fetches from remote when local is empty`() = runBlockingTest {
        `when`(localDataSource.getMovies()).thenReturn(flowOf(emptyList()))
        val remoteMovies = MovieDbResultDataModel(1, 10, 100, listOf(MovieDataModel(false, listOf(1), 1, "en", "Movie 1", "Overview", 8.5, "poster.jpg", "2023-10-10", "Movie", false, 9.0, 100)))
        `when`(remoteDataSource.fetchNowPlayingMovies()).thenReturn(DataState.Success(remoteMovies))

        val result = repository.fetchNowPlayingMovies()

        assertTrue(result is ResultState.Success)
        assertEquals(remoteMovies.results.size, (result as ResultState.Success).data.size)
    }

    @Test
    fun `fetchNowPlayingMovies returns error when exception occurs`() = runTest {

        `when`(localDataSource.getMovies()).thenReturn(flowOf(emptyList()))
        `when`(remoteDataSource.fetchNowPlayingMovies()).thenReturn(DataState.Error("No internet connection or server unreachable"))


        val result = repository.fetchNowPlayingMovies()

        assertTrue(result is ResultState.Error)
        assertEquals("No internet connection or server unreachable", (result as ResultState.Error).message)
    }

}




