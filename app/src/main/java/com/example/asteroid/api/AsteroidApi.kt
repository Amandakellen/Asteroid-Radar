package com.example.asteroid.api

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "nHgvcqUO36pOHBCBMOEumAbVnDfycNYNJZddQkxx"

interface AsteroidApi {

    @GET("neo/rest/v1/neo/browse")
    suspend fun getAsteroids(
        @Query("api_key") apiKey: String = API_KEY
    ): Response<ResponseBody>
}
