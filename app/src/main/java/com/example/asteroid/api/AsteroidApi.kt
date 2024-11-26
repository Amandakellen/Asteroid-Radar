package com.example.asteroid.api

import com.example.asteroid.Constants
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "https://api.nasa.gov/"

interface AsteroidApi {

    @GET("neo/rest/v1/neo/browse")
    suspend fun getAsteroids(
        @Query("api_key") apiKey: String = API_KEY
    ): JSONObject
}
