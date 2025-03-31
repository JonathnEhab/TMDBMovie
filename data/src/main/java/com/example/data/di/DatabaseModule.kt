package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.dao.MovieDao
import com.example.data.local.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun getRoomInstance (@ApplicationContext context: Context) : MovieDatabase {
        return Room.databaseBuilder(context
            , MovieDatabase::class.java
            , "Movies Database")
            .fallbackToDestructiveMigration()
            .build()
    }
    @Singleton
    @Provides
    fun getMyDao (movieDatabase: MovieDatabase) : MovieDao {
        return movieDatabase.getDao()
    }
}