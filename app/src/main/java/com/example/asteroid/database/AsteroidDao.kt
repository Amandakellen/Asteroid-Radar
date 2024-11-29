package com.example.asteroid.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.asteroid.data.Asteroid
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Dao
interface AsteroidDao {
    @Query("SELECT * FROM asteroids WHERE closeApproachDate >= :startDate ORDER BY closeApproachDate ASC")
    fun getAsteroidsFromDate(startDate: String): LiveData<List<DatabaseAsteroid>>

    @Query("SELECT * FROM asteroids ORDER BY closeApproachDate ASC")
    fun getAllAsteroids(): LiveData<List<DatabaseAsteroid>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg asteroids: DatabaseAsteroid)


    @Query("DELETE FROM asteroids WHERE closeApproachDate < :today")
    suspend fun deleteOldAsteroids(today: String)

    @RequiresApi(Build.VERSION_CODES.O)
    @Query("SELECT * FROM asteroids WHERE closeApproachDate = :today ORDER BY closeApproachDate ASC")
    fun getTodayAsteroids(today: String = getCurrentDate()): LiveData<List<Asteroid>>

    @RequiresApi(Build.VERSION_CODES.O)
    @Query("SELECT * FROM asteroids WHERE closeApproachDate BETWEEN :startDate AND :endDate ORDER BY closeApproachDate ASC")
    fun getWeekAsteroids(startDate: String = getCurrentDate(), endDate: String = getSevenDaysFromNow()): LiveData<List<Asteroid>>

}

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDate(): String {
    val today = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return today.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getSevenDaysFromNow(): String {
    val today = LocalDate.now()
    val sevenDaysFromNow = today.plusDays(7)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return sevenDaysFromNow.format(formatter)
}
