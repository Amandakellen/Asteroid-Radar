package com.example.asteroid.api

import com.example.asteroid.Asteroid
import com.example.asteroid.Constants.API_KEY
import com.example.asteroid.Constants.BASE_URL
import com.example.asteroid.PictureOfDay
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidService {
    @GET("neo/rest/v1/feed")
    suspend fun getNeoData(
        @Query("start_date") startDate: String? = null,
        @Query("end_date") endDate: String? = null,
        @Query("api_key") apiKey: String = API_KEY
    ): String

    @GET("planetary/apod")
    suspend fun getPictureOfDay(@Query("api_key") apiKey: String = API_KEY): PictureOfDay

    companion object {
        fun create(): AsteroidService {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()

            return retrofit.create(AsteroidService::class.java)
        }


        fun getAsteroids(jsonResult: JSONObject): List<Asteroid> {
            return parseAsteroidsJsonResult(jsonResult)
        }
    }
}