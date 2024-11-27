package com.example.asteroid.api

import com.example.asteroid.Constants.API_KEY
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidApi {

    @GET("neo/rest/v1/neo/browse")
    suspend fun getAsteroids(
        @Query("api_key") apiKey: String = API_KEY
    ): Response<ResponseBody>
}
