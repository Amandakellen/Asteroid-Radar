package com.example.asteroid.api

import com.example.asteroid.Asteroid
import com.example.asteroid.Constants.API_KEY
import com.example.asteroid.Constants.BASE_URL
import com.example.asteroid.data.PictureOfDay
import com.example.asteroid.data.NeoResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
    ): NeoResponse

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


        fun getAsteroids(neoResponse: NeoResponse): List<Asteroid> {
            val asteroids = mutableListOf<Asteroid>()

            neoResponse.near_earth_objects.values.forEach { neoList ->
                neoList.forEach { nearEarthObject ->
                    val asteroid = Asteroid(
                        id = nearEarthObject.id.toLong(),
                        codename = nearEarthObject.name,
                        closeApproachDate = nearEarthObject.close_approach_data.first().close_approach_date,
                        absoluteMagnitude = nearEarthObject.absolute_magnitude_h,
                        estimatedDiameter = nearEarthObject.estimated_diameter.kilometers.estimated_diameter_max,
                        relativeVelocity = nearEarthObject.close_approach_data.first().relative_velocity.kilometers_per_second.toDouble(),
                        distanceFromEarth = nearEarthObject.close_approach_data.first().miss_distance.kilometers.toDouble(),
                        isPotentiallyHazardous = nearEarthObject.is_potentially_hazardous_asteroid
                    )
                    asteroids.add(asteroid)
                }
            }

            return asteroids
        }
    }
}