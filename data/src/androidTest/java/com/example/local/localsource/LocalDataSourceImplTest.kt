package com.example.local.localsource

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.local.dao.MovieDao
import com.example.data.local.database.MovieDatabase
import com.example.data.local.localmodel.MovieDetailsEntity
import com.example.data.local.localmodel.MovieEntity
import com.example.data.local.localsource.LocalDataSourceImpl
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class LocalDataSourceImplTest {

    private lateinit var database: MovieDatabase
    private lateinit var movieDao: MovieDao
    private lateinit var localDataSource: LocalDataSourceImpl

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, MovieDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        movieDao = database.getDao()
        localDataSource = LocalDataSourceImpl(movieDao)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndRetrieveMovies() = runTest {

        val movies = listOf(
            MovieEntity(1, "Popeye the Slayer Man", "A horror movie", "/7NLY1jNwtZX1yVzwVoBeAhaBE8i.jpg", 4.5),
            MovieEntity(2, "Another Movie", "Another horror movie", "/7NLY1jNwtZX1yVzwVoBeAhaBE8i.jpg",  6.5)
        )


        localDataSource.insertMovies(movies)


        val result = localDataSource.getMovies().first()

        assertTrue(result.isNotEmpty())
        assertEquals(movies.size, result.size)
        assertEquals(movies[0].title, result[0].title)
    }

    @Test
    fun insertAndRetrieveMovieDetails() = runTest {

        val movieDetails = MovieDetailsEntity(
            id = 1,
            title = "Detailed Movie",
            overview = "This is a test overview.",
            budget = 5000000,
            popularity = 8.7,
            posterPath = "/details.jpg",
            releaseDate = "2025-02-01",
            runtime = 120,
            tagline = "Test Tagline",
            voteAverage = 9.0,
            voteCount = 1000
        )


        localDataSource.insertMovieDetails(movieDetails)


        val result = localDataSource.getMovieDetails(1).first()

        assertNotNull(result)
        assertEquals(movieDetails.title, result?.title)
        assertEquals(movieDetails.budget, result?.budget)
    }
}
