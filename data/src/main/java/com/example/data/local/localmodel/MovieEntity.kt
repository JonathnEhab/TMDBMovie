package com.example.data.local.localmodel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.BuildConfig
import com.example.data.network.modelnetwork.MovieDataModel
import com.example.domain.models.MovieDomainModel

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val posterPath: String?,
    val releaseDate: String,
    val voteAverage: Double
)

fun MovieDataModel.toEntity(): MovieEntity {
    return MovieEntity(
        id = this.id,
        title = this.title,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage
    )
}
fun MovieEntity.toDomainModel(): MovieDomainModel {
    return MovieDomainModel(
        id = this.id,
        title = this.title,
        posterPath = "${BuildConfig.IMAGE_URL}${this.posterPath}",
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage
    )
}





