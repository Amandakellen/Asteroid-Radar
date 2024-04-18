package com.example.asteroid.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.asteroid.Asteroid
import com.example.asteroid.Constants.API_QUERY_DATE_FORMAT
import com.example.asteroid.api.AsteroidService
import com.example.asteroid.data.PictureOfDay
import com.example.asteroid.database.AsteroidsDatabase
import com.example.asteroid.database.asDomainModel
import com.example.asteroid.database.toDatabaseAsteroidList
import com.example.asteroid.database.toEntity
import com.example.asteroid.database.toPictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class AsteroidRepository(private val database: AsteroidsDatabase) {
    private val today =
        LocalDateTime.now().format(DateTimeFormatter.ofPattern(API_QUERY_DATE_FORMAT))
//    val asteroids: LiveData<List<Asteroid>> = database.asteroidDao.getAsteroids().map {
//        it.asDomainModel()
//    }
//
//    val pictureOfDay: LiveData<PictureOfDay> = database.asteroidDao.getImage(today).map {
//        it.toPictureOfDay()
//    }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val apiService = AsteroidService.create()

            val neoDataWithDates = apiService.getNeoData(today, today)
            val asteroidList = AsteroidService.getAsteroids(neoDataWithDates)

            val databaseAsteroidList = asteroidList.toDatabaseAsteroidList()

            database.asteroidDao.insertAll(*databaseAsteroidList.toTypedArray())
        }
    }

    suspend fun refreshImageDay(){
        withContext(Dispatchers.IO) {
            val apiService = AsteroidService.create()
            val imageDay = apiService.getPictureOfDay()

            val imageDayData =  imageDay.toEntity()

            database.asteroidDao.insertImage(imageDayData)
        }
    }
}
