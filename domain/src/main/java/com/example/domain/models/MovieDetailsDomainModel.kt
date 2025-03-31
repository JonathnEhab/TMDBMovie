package com.example.domain.models

data class MovieDetailsDomainModel(
    val id: Int,
    val tagline: String?,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseDate: String,
    val popularity: Double,
    val runtime: Int?,
    val overview: String?,
    val budget: Int,
    val posterPath: String?,
)