package com.example.asteroid.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.asteroid.Asteroid
import com.example.asteroid.Constants
import com.example.asteroid.data.PictureOfDay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
data class DatabaseAsteroid(
    @PrimaryKey
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

@Entity
data class DatabasePictureDay(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val mediaType: String,
    val title: String,
    val url: String,
    val date: String
)

@RequiresApi(Build.VERSION_CODES.O)
fun PictureOfDay.toEntity(): DatabasePictureDay {
    return DatabasePictureDay(
        mediaType = this.mediaType,
        title = this.title,
        url = this.url,
        date = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern(Constants.API_QUERY_DATE_FORMAT))
    )
}


fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}


fun List<Asteroid>.toDatabaseAsteroidList(): List<DatabaseAsteroid> {
    return map { asteroid ->
        DatabaseAsteroid(
            id = asteroid.id,
            codename = asteroid.codename,
            closeApproachDate = asteroid.closeApproachDate,
            absoluteMagnitude = asteroid.absoluteMagnitude,
            estimatedDiameter = asteroid.estimatedDiameter,
            relativeVelocity = asteroid.relativeVelocity,
            distanceFromEarth = asteroid.distanceFromEarth,
            isPotentiallyHazardous = asteroid.isPotentiallyHazardous
        )
    }
}

fun DatabasePictureDay.toPictureOfDay(): PictureOfDay {
    return PictureOfDay(
        mediaType = this.mediaType,
        title = this.title,
        url = this.url
    )
}