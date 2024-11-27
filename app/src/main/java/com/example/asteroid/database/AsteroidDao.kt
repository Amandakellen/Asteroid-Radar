package com.example.asteroid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.asteroid.Asteroid

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
}