package com.example.asteroid.repository

import android.util.Log
import com.example.asteroid.Asteroid
import com.example.asteroid.api.AsteroidApi
import com.example.asteroid.api.parseAsteroidsJsonResult
import org.json.JSONObject
import java.io.IOException

class AsteroidRepository(private val asteroidApi: AsteroidApi) {

    suspend fun getAsteroidsFromApi(): List<Asteroid> {
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
                    return parseAsteroidsJsonResult(jsonResult)
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
}
