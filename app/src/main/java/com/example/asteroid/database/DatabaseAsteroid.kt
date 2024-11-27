package com.example.asteroid.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.asteroid.Asteroid

@Entity(tableName = "asteroids")
data class DatabaseAsteroid(
    @PrimaryKey val id: Long,
    val codename: String,
    val closeApproachDate: List<String>,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)


