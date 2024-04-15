package com.example.asteroid.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.asteroid.Asteroid
import com.example.asteroid.Constants.API_QUERY_DATE_FORMAT
import com.example.asteroid.api.AsteroidAPI
import com.example.asteroid.database.AsteroidsDatabase
import com.example.asteroid.database.asDomainModel
import com.example.asteroid.database.toDatabaseAsteroidList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AsteroidRepository(private val database: AsteroidsDatabase) {
    @RequiresApi(Build.VERSION_CODES.O)
    private val today =
        LocalDateTime.now().format(DateTimeFormatter.ofPattern(API_QUERY_DATE_FORMAT))
    val asteroids: LiveData<List<Asteroid>> = database.asteroidDao.getAsteroids().map {
        it.asDomainModel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val apiService = AsteroidAPI.create()

            val neoDataWithDates = apiService.getNeoData(today, today)
            val asteroidList = AsteroidAPI.getAsteroids(JSONObject(neoDataWithDates))

            val databaseAsteroidList = asteroidList.toDatabaseAsteroidList()

            database.asteroidDao.insertAll(*databaseAsteroidList.toTypedArray())
        }
    }
}
