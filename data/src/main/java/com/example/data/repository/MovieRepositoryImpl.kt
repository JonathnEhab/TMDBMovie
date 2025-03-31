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

            val localMovies = localDataSource.getMovies().firstOrNull()

            if (!localMovies.isNullOrEmpty()) {
                return ResultState.Success(localMovies.map { it.toDomainModel() })
            }


            val response = remoteDataSource.fetchNowPlayingMovies()
            when (response) {
                is DataState.Success -> {
                    val moviesEntities = response.data.results.map { it.toEntity() }
                    localDataSource.insertMovies(moviesEntities)

                    moviesEntities.forEach { movie ->
                        fetchAndStoreMovieDetails(movie.id)
                    }

                    ResultState.Success(moviesEntities.map { it.toDomainModel() })
                }
                is DataState.Error -> {
                    ResultState.Error(response.exception)
                }
            }
        } catch (e: Exception) {
            ResultState.Error(ExceptionHandler.handleException(e))
        }
    }
    override suspend fun fetchMovieDetails(movieId: Int): ResultState<MovieDetailsDomainModel> {
        return try {

            val localMovieDetails = localDataSource.getMovieDetails(movieId).firstOrNull()
            if (localMovieDetails != null) {
                return ResultState.Success(localMovieDetails.toDomainModel())
            }


            val response = remoteDataSource.fetchMovieDetails(movieId)
            when (response) {
                is DataState.Success -> {
                    val movieEntity = response.data.toEntity()
                    localDataSource.insertMovieDetails(movieEntity)
                    ResultState.Success(movieEntity.toDomainModel())
                }
                is DataState.Error -> {
                    ResultState.Error(response.exception)
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


}

