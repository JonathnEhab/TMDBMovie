package com.example.local.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.local.dao.MovieDao
import com.example.data.local.database.MovieDatabase
import com.example.data.local.localmodel.MovieDetailsEntity
import com.example.data.local.localmodel.MovieEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    private lateinit var database: MovieDatabase
    private lateinit var movieDao: MovieDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()

        movieDao = database.getDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAndRetrieveNowPlayingMovies() = runBlocking {

        val movie = MovieEntity(
            id = 1261050,
            title = "The Quiet Ones",
            posterPath = "/7NLY1jNwtZX1yVzwVoBeAhaBE8i.jpg",
            releaseDate = "2025-01-01",
            voteAverage = 4.5
        )


        movieDao.insertMovieDbResultNowPlaying(movie)

        val movies = movieDao.getNowPlayingMovies().first()


        assertTrue(movies.isNotEmpty())
    }

    @Test
    fun insertAndRetrieveMovieDetails() = runBlocking {

        val movieDetails = MovieDetailsEntity(
            id = 1388366,
            title = "Popeye the Slayer Man",
            overview = "A curious group of friends sneak into an abandoned spinach canning factory to film a documentary on the legend of the \"Sailor Man,\" who is said to haunt the factory and local docks.",
            budget = 123,
            popularity = 231.6278,
            posterPath = "/nVwu3mN7hr1yF467pGct3yQFM41.jpg",
            releaseDate = "2025-03-21",
            runtime = 88,
            tagline = "",
            voteAverage = 5.92,
            voteCount = 25
        )


        movieDao.insertMovieDetails(movieDetails)


        val retrievedDetails = movieDao.getMovieDetails(1388366).first()


        assertNotNull(retrievedDetails)
        assertTrue(retrievedDetails?.title == "Popeye the Slayer Man")
    }
}
