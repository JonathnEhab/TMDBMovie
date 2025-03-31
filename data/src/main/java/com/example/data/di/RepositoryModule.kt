package com.example.data.di

import com.example.data.local.dao.MovieDao
import com.example.data.local.localsource.LocalDataSource
import com.example.data.local.localsource.LocalDataSourceImpl
import com.example.data.network.remotesource.RemoteDataSource
import com.example.data.network.remotesource.RemoteDataSourceImpl
import com.example.data.network.service.ApiService
import com.example.data.repository.MovieRepositoryImpl
import com.example.domain.repostiory.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): MovieRepository {
        return MovieRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource {
        return RemoteDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(movieDao: MovieDao): LocalDataSource {
        return LocalDataSourceImpl(movieDao)
    }
}
