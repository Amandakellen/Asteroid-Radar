package com.example.asteroid.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.asteroid.Asteroid
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
            // Faz a chamada para a API e obtém a resposta
            val response = asteroidApi.getAsteroids()

            // Verifica se a resposta é bem-sucedida
            if (response.isSuccessful) {
                // Converte o corpo da resposta para string
                val responseBody = response.body()?.string()

                // Verifica se o corpo da resposta não é nulo
                if (responseBody != null) {
                    // Converte a resposta para JSONObject
                    val jsonResult = JSONObject(responseBody)
                    Log.d("AsteroidRepository", "JSON Response: $jsonResult")

                    // Faz o parse do JSON para uma lista de objetos Asteroid
                    val asteroids = parseAsteroidsJsonResult(jsonResult)

                    // Salva os asteroides no banco de dados
                    val databaseAsteroids = asteroids.map { asteroid ->
                        asteroid.toDatabaseAsteroid()
                    }

                    // Salva os asteroides no banco de dados
                    asteroidDao.insertAll(*databaseAsteroids.toTypedArray())
                } else {
                    throw IOException("Resposta da API vazia")
                }
            } else {
                throw IOException("Erro na resposta da API: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            // Caso ocorra algum erro, você pode tratar aqui
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
