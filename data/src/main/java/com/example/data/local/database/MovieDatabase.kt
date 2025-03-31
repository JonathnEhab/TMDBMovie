package com.example.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.local.converters.StringListTypeConverter
import com.example.data.local.dao.MovieDao
import com.example.data.local.localmodel.MovieDetailsEntity
import com.example.data.local.localmodel.MovieEntity

@Database(entities = [MovieDetailsEntity::class, MovieEntity::class], version = 2, exportSchema = false)
abstract class MovieDatabase :RoomDatabase() {
    abstract fun getDao(): MovieDao
}