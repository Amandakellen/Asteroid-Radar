package com.example.asteroid.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.asteroid.data.Asteroid
import com.example.asteroid.api.AsteroidApi
import com.example.asteroid.api.parseAsteroidsJsonResult
import com.example.asteroid.database.AsteroidDao
import com.example.asteroid.database.toAsteroid
import com.example.asteroid.database.toDatabaseAsteroid
import org.json.JSONObject
import java.io.IOException

class AsteroidRepository(private val asteroidApi: AsteroidApi, private val asteroidDao: AsteroidDao,) {

    suspend fun getAsteroidsFromApi(){
        try {
            val response = asteroidApi.getAsteroids()

            if (response.isSuccessful) {
                val responseBody = response.body()?.string()

                if (responseBody != null) {
                    val jsonResult = JSONObject(responseBody)
                    Log.d("AsteroidRepository", "JSON Response: $jsonResult")

                    val asteroids = parseAsteroidsJsonResult(jsonResult)

                    val databaseAsteroids = asteroids.map { asteroid ->
                        asteroid.toDatabaseAsteroid()
                    }

                    asteroidDao.insertAll(*databaseAsteroids.toTypedArray())
                } else {
                    throw IOException("Resposta da API vazia")
                }
            } else {
                throw IOException("Erro na resposta da API: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            throw IOException("Erro ao recuperar dados da API da NASA", e)
        }
    }

    fun getAllAsteroidsFromDatabase(): LiveData<List<Asteroid>> {
        return asteroidDao.getAllAsteroids().map { databaseAsteroids ->
            databaseAsteroids.map { it.toAsteroid() }
        }
    }

    fun getAsteroidsFromDatabase(startDate: String): LiveData<List<Asteroid>> {
        return asteroidDao.getAsteroidsFromDate(startDate).map { databaseAsteroids ->
            databaseAsteroids.map { it.toAsteroid() }
        }
    }

    suspend fun deleteOldAsteroids(today: String) {
        asteroidDao.deleteOldAsteroids(today)
    }
}
