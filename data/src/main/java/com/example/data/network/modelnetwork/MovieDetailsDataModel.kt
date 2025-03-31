package com.example.data.network.modelnetwork


import com.google.gson.annotations.SerializedName

data class MovieDetailsDataModel(
    @SerializedName("id")
    val id: Int,

    @SerializedName("tagline")
    val tagline: String?,

    @SerializedName("title")
    val title: String,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")
    val voteCount: Int,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("runtime")
    val runtime: Int?,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("budget")
    val budget: Int,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("backdrop_path")
    val backdropPath: String?
)







