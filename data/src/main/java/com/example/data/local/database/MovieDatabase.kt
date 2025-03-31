package com.example.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.MovieDao
import com.example.data.local.localmodel.MovieDetailsEntity
import com.example.data.local.localmodel.MovieEntity

@Database(entities = [MovieDetailsEntity::class, MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase :RoomDatabase() {
    abstract fun getDao(): MovieDao
}