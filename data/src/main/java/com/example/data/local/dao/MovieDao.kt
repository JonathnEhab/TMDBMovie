package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.localmodel.MovieDetailsEntity
import com.example.data.local.localmodel.MovieEntity

import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertMovieDbResultNowPlaying(movie: MovieEntity)

        @Query("SELECT * FROM movies")
        fun getNowPlayingMovies(): Flow<List<MovieEntity>>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertMovieDetails(movie: MovieDetailsEntity)

        @Query("SELECT * FROM movie_details WHERE id = :movieId")
        fun getMovieDetails(movieId: Int): Flow<MovieDetailsEntity?>


}
