package com.example.remote.apiservice

import com.example.data.network.service.ApiService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `fetchNowPlayingMovies returns expected data`() = runBlocking {

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(""" 
                {
                    "results": [
                        {
                            "id": 1,
                            "title": "Mock Movie",
                            "overview": "This is a test overview",
                            "popularity": 9.8,
                            "poster_path": "/mock.jpg"
                        }
                    ]
                }
            """.trimIndent())
        mockWebServer.enqueue(mockResponse)


        val response = apiService.fetchNowPlayingMovies()


        assertEquals(200, response.code())
        assertEquals("Mock Movie", response.body()?.results?.get(0)?.title)
        assertEquals("/mock.jpg", response.body()?.results?.get(0)?.posterPath)
    }

    @Test
    fun `fetchMovieDetails returns expected data`() = runBlocking {

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(""" 
                {
                    "id": 1,
                    "title": "Detailed Mock Movie",
                    "overview": "Detailed overview",
                    "budget": 1000000,
                    "popularity": 8.7,
                    "poster_path": "/detailed_mock.jpg"
                }
            """.trimIndent())
        mockWebServer.enqueue(mockResponse)


        val response = apiService.fetchMovieDetails(1)


        assertEquals(200, response.code())
        assertEquals("Detailed Mock Movie", response.body()?.title)
        assertEquals(1000000, response.body()?.budget)
    }
}
