package com.example.data.local.localsource


import com.example.data.local.localmodel.MovieDetailsEntity
import com.example.data.local.localmodel.MovieEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
        suspend fun insertMovies(movies: List<MovieEntity>)
        suspend fun insertMovieDetails(movie: MovieDetailsEntity)



        fun getMovies(): Flow<List<MovieEntity>>
        fun getMovieDetails(movieId: Int): Flow<MovieDetailsEntity?>

}
