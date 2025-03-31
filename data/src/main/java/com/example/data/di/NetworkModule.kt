package com.example.data.di

import com.example.data.BuildConfig
import com.example.data.network.service.ApiService
import com.example.util.APIEndPoint
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoviesInterceptor(): Interceptor {
        return Interceptor {
            val call = it.request()
            val response = call.url.newBuilder()
                .addQueryParameter(
                    APIEndPoint.APIKey
                    , BuildConfig.API_KEY)
                .build()
            val request = call.newBuilder()
                .url(response).build()
            it.proceed(request)

        }
    }

    @Provides
    @Singleton
    fun provideHttpLoginInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .apply {
            level= HttpLoggingInterceptor.Level.BODY
        }


    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: Interceptor,
                            loggingInterceptor: HttpLoggingInterceptor)
            : OkHttpClient {
        val okHttpClient =  OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor)
            .build()
        return okHttpClient
    }
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val retrofit= Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
        return retrofit
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        val apiService =retrofit.create(ApiService::class.java)
        return apiService
    }
}