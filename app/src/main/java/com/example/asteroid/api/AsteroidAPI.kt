package com.example.asteroid.api

import com.example.asteroid.Asteroid
import com.example.asteroid.Constants.API_KEY
import com.example.asteroid.Constants.BASE_URL
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidAPI {

    @GET("feed")
    suspend fun getNeoData(
        @Query("start_date") startDate: String? = null,
        @Query("end_date") endDate: String? = null,
        @Query("api_key") apiKey: String = API_KEY
    ): String

    companion object {
        fun create(): AsteroidAPI {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()

            return retrofit.create(AsteroidAPI::class.java)
        }

        fun getAsteroids(jsonResult: JSONObject): List<Asteroid> {
            return parseAsteroidsJsonResult(jsonResult)
        }
    }
}