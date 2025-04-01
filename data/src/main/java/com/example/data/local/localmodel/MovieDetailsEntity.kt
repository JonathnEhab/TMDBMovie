package com.example.data.local.localmodel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.BuildConfig
import com.example.data.network.modelnetwork.MovieDetailsDataModel
import com.example.domain.models.MovieDetailsDomainModel

@Entity(tableName = "movie_details")
data class MovieDetailsEntity(
    @PrimaryKey val id: Int,
    val tagline: String?,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseDate: String,
    val popularity: Double,
    val runtime: Int?,
    val overview: String?,
    val budget: Int,
    val posterPath: String?
)

fun MovieDetailsDataModel.toEntity(): MovieDetailsEntity {
    return MovieDetailsEntity(
        id = this.id,
        tagline = this.tagline,
        title = this.title,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        releaseDate = this.releaseDate,
        popularity = this.popularity,
        runtime = this.runtime,
        overview = this.overview,
        budget = this.budget,
        posterPath = this.posterPath
    )
}

fun MovieDetailsEntity.toDomainModel(): MovieDetailsDomainModel {
    return MovieDetailsDomainModel(
        id = this.id,
        tagline = this.tagline,
        title = this.title,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        releaseDate = this.releaseDate,
        popularity = this.popularity,
        runtime = this.runtime,
        overview = this.overview,
        budget = this.budget,
        posterPath = this.posterPath?.let { "${BuildConfig.IMAGE_URL}$it" }
    )
}

