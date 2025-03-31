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
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class MovieRepositoryImplTest {

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Mock
    lateinit var localDataSource: LocalDataSource

    private lateinit var repository: MovieRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = MovieRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `get movies from internet successfully`() = runTest {

        val remoteMovies = listOf(
            MovieDataModel(
                id = 1,
                title = "Movie 1",
                posterPath = "path1",
                voteAverage = 2.5,
                releaseDate = "2025-05-04",
                voteCount = 5,
                originalTitle = "nasdsand",
                originalLanguage = "en",
                adult = true,
                video = true,
                genreIds = listOf(1,2,3),
                overview = "kdsmcfsdlc",
                popularity = 2.5
            ),
            MovieDataModel(
                id = 2,
                title = "Movie 2",
                posterPath = "path2",
                voteAverage = 3.5,
                releaseDate = "2025-05-05",
                voteCount = 6,
                originalTitle = "nasdsand2",
                originalLanguage = "en",
                adult = false,
                video = false,
                genreIds = listOf(4,5,6),
                overview = "kdsmcfsdlc2",
                popularity = 3.5
            )
        )

        val dbResult = MovieDbResultDataModel(
            page = 1,
            totalPages = 1,
            totalResults = 2,
            results = remoteMovies
        )


        `when`(remoteDataSource.fetchNowPlayingMovies())
            .thenReturn(DataState.Success(dbResult))

        val movieEntities = remoteMovies.map { it.toEntity() }
        `when`(localDataSource.getMovies())
            .thenReturn(flowOf(movieEntities))


        val result = repository.fetchNowPlayingMovies()


        assert(result is ResultState.Success)
        assertEquals(2, (result as ResultState.Success).data.size)


        verify(localDataSource).insertMovies(movieEntities)
        verify(remoteDataSource, times(2)).fetchMovieDetails(anyInt())
    }

    @Test
    fun `fallback to local when internet fails`() = runTest {

        `when`(remoteDataSource.fetchNowPlayingMovies())
            .thenReturn(DataState.Error("No internet"))


        val localMovies = listOf(
            MovieEntity(
                id = 1,
                title = "Local Movie 1",
                posterPath = "local_path1",
                releaseDate = "2025-01-01",
                voteAverage = 4.0
            ),
            MovieEntity(
                id = 2,
                title = "Local Movie 2",
                posterPath = "local_path2",
                releaseDate = "2025-01-02",
                voteAverage = 4.5
            )
        )

        `when`(localDataSource.getMovies())
            .thenReturn(flowOf(localMovies))


        val result = repository.fetchNowPlayingMovies()


        assert(result is ResultState.Success)
        assertEquals(2, (result as ResultState.Success).data.size)


        verify(localDataSource, never()).insertMovies(anyList())
    }

    @Test
    fun `return error when both sources fail`() = runTest {

        `when`(remoteDataSource.fetchNowPlayingMovies())
            .thenReturn(DataState.Error("No internet"))

        `when`(localDataSource.getMovies())
            .thenReturn(flowOf(emptyList()))

        // Call the function
        val result = repository.fetchNowPlayingMovies()


        assert(result is ResultState.Error)
        assertEquals("No data available in local database", (result as ResultState.Error).message)
    }

    @Test
    fun `get movie details successfully`() = runTest {

        val remoteDetails = MovieDetailsDataModel(
            id = 1,
            tagline = "Great movie",
            title = "Movie Details",
            voteAverage = 8.5,
            voteCount = 1000,
            releaseDate = "2025-05-01",
            popularity = 100.0,
            runtime = 120,
            overview = "Test overview",
            budget = 1000000,
            posterPath = "poster_path",
            backdropPath = "backdrop_path"
        )


        `when`(remoteDataSource.fetchMovieDetails(1))
            .thenReturn(DataState.Success(remoteDetails))

        val detailsEntity = remoteDetails.toEntity()
        `when`(localDataSource.getMovieDetails(1))
            .thenReturn(flowOf(detailsEntity))


        val result = repository.fetchMovieDetails(1)


        assert(result is ResultState.Success)
        assertEquals(1, (result as ResultState.Success).data.id)


        verify(localDataSource).insertMovieDetails(detailsEntity)
    }
}