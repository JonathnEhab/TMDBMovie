package com.example.data.repository

import android.util.Log
import com.example.data.local.localmodel.toDomainModel
import com.example.data.local.localmodel.toEntity
import com.example.data.local.localsource.LocalDataSource

import com.example.data.network.remotesource.RemoteDataSource
import com.example.data.network.state.DataState
import com.example.domain.models.MovieDetailsDomainModel
import com.example.domain.models.MovieDomainModel

import com.example.domain.repostiory.MovieRepository
import com.example.domain.state.ResultState
import com.example.util.ExceptionHandler
import kotlinx.coroutines.flow.firstOrNull

import javax.inject.Inject
import kotlin.math.log

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : MovieRepository {

    override suspend fun fetchNowPlayingMovies(): ResultState<List<MovieDomainModel>> {
        return try {
            val response = remoteDataSource.fetchNowPlayingMovies()

            when (response) {
                is DataState.Success -> {
                    val moviesEntities = response.data.results.map { it.toEntity() }
                    localDataSource.insertMovies(moviesEntities)


                    moviesEntities.forEach { movie ->
                        fetchAndStoreMovieDetails(movie.id)
                    }

                    getLocalMovies()
                }

                is DataState.Error -> {
                    getLocalMovies()
                }
            }
        } catch (e: Exception) {
            ResultState.Error(ExceptionHandler.handleException(e))

        }
    }

    override suspend fun fetchMovieDetails(movieId: Int): ResultState<MovieDetailsDomainModel> {
        return try {
            val response = remoteDataSource.fetchMovieDetails(movieId)

            when (response) {
                is DataState.Success -> {
                    val movieEntity = response.data.toEntity()
                    localDataSource.insertMovieDetails(movieEntity)
                    ResultState.Success(movieEntity.toDomainModel())
                }

                is DataState.Error -> {
                    getLocalMovieDetails(movieId)
                }
            }
        } catch (e: Exception) {
            ResultState.Error(ExceptionHandler.handleException(e))

        }
    }


    private suspend fun fetchAndStoreMovieDetails(movieId: Int) {
        when (val response = remoteDataSource.fetchMovieDetails(movieId)) {
            is DataState.Success -> {
                val movieEntity = response.data.toEntity()
                localDataSource.insertMovieDetails(movieEntity)
            }
            is DataState.Error -> {
                Log.d("MovieRepositoryImpl", "fetchAndStoreMovieDetails: Can't insert movies")


            }
        }
    }


    private suspend fun getLocalMovies(): ResultState<List<MovieDomainModel>> {
        val localMovies = localDataSource.getMovies().firstOrNull()
        return if (!localMovies.isNullOrEmpty()) {
            ResultState.Success(localMovies.map { it.toDomainModel() })
        } else {
            ResultState.Error("No data available in local database")
        }
    }


    private suspend fun getLocalMovieDetails(movieId: Int): ResultState<MovieDetailsDomainModel> {
        val localMovieDetails = localDataSource.getMovieDetails(movieId).firstOrNull()

        Log.d("MovieRepositoryImpl", "Local DB - PosterPath: ${localMovieDetails?.posterPath}")

        return localMovieDetails?.let {
            ResultState.Success(it.toDomainModel())
        } ?: ResultState.Error("No details found for movie ID: $movieId in local database")
    }

}

