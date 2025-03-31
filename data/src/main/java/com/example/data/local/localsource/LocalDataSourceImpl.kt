package com.example.data.local.localsource

import com.example.data.local.dao.MovieDao
import com.example.data.local.localmodel.MovieDetailsEntity
import com.example.data.local.localmodel.MovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val movieDao: MovieDao) : LocalDataSource {
    override suspend fun insertMovies(movies: List<MovieEntity>) {
        movies.forEach { movieDao.insertMovieDbResultNowPlaying(it) }
    }

    override suspend fun insertMovieDetails(movie: MovieDetailsEntity) {
        movieDao.insertMovieDetails(movie)
    }

    override fun getMovies(): Flow<List<MovieEntity>> {
        return movieDao.getNowPlayingMovies()
    }

    override fun getMovieDetails(movieId: Int): Flow<MovieDetailsEntity?> {
        return movieDao.getMovieDetails(movieId)
    }

}

