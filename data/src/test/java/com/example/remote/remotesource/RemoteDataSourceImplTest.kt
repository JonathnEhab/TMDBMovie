package com.example.remote.remotesource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.network.remotesource.RemoteDataSourceImpl
import com.example.data.network.service.ApiService
import com.example.data.network.state.DataState
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class RemoteDataSourceImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService
    private lateinit var remoteDataSource: RemoteDataSourceImpl

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/")) // عنوان مزيف لمحاكاة الـ API
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
        remoteDataSource = RemoteDataSourceImpl(apiService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown() // إيقاف السيرفر بعد كل اختبار
    }

    @Test
    fun `fetchNowPlayingMovies should return success when API responds correctly`() = runTest {
        // محاكاة استجابة الـ API الناجحة
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("{ \"results\": [{ \"id\": 1, \"title\": \"Test Movie\", \"overview\": \"Test Overview\" }] }")
        mockWebServer.enqueue(mockResponse)


        val result = remoteDataSource.fetchNowPlayingMovies()


        assertTrue(result is DataState.Success)
        assertEquals(1, (result as DataState.Success).data.results.size)
        assertEquals("Test Movie", result.data.results[0].title)
    }

    @Test
    fun `fetchNowPlayingMovies should return error when API responds with failure`() = runTest {

        val mockResponse = MockResponse().setResponseCode(404)
        mockWebServer.enqueue(mockResponse)

        val result = remoteDataSource.fetchNowPlayingMovies()


        assertTrue(result is DataState.Error)
    }

    @Test
    fun `fetchMovieDetails should return success when API responds correctly`() = runTest {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("{ \"id\": 1, \"title\": \"Detailed Movie\", \"overview\": \"Test Overview\" }")
        mockWebServer.enqueue(mockResponse)

        val result = remoteDataSource.fetchMovieDetails(1)

        assertTrue(result is DataState.Success)
        assertEquals("Detailed Movie", (result as DataState.Success).data.title)
    }

    @Test
    fun `fetchMovieDetails should return error when API responds with failure`() = runTest {
        val mockResponse = MockResponse().setResponseCode(500)
        mockWebServer.enqueue(mockResponse)

        val result = remoteDataSource.fetchMovieDetails(1)

        assertTrue(result is DataState.Error)
    }
}
