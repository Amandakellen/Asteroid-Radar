package com.example.asteroid.repository

import com.example.asteroid.Asteroid
import com.example.asteroid.api.AsteroidApi
import com.example.asteroid.api.parseAsteroidsJsonResult

class AsteroidRepository(private val asteroidApi: AsteroidApi) {

    suspend fun getAsteroidsFromApi(): List<Asteroid> {
        val response = asteroidApi.getAsteroids()
        return parseAsteroidsJsonResult(response)
    }
}
