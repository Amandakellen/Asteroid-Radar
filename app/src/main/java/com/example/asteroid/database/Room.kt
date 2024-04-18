package com.example.asteroid.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Dao
interface AsteroidsDao{
    @Query("select * from databaseasteroid")
    fun getAsteroids(): LiveData<List<DatabaseAsteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg Asteroids: DatabaseAsteroid)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImage(vararg image: DatabasePictureDay)

    @Query("SELECT * FROM databasepictureday WHERE date = :date")
    fun getImage(date: String): LiveData<DatabasePictureDay>

}

@Database(entities = [DatabaseAsteroid::class, DatabasePictureDay::class], version = 2)
abstract class AsteroidsDatabase: RoomDatabase(){
    abstract val asteroidDao: AsteroidsDao
}

private lateinit var INSTANCE: AsteroidsDatabase

fun getDatabase(context: Context): AsteroidsDatabase{
    if(!::INSTANCE.isInitialized){
        INSTANCE = Room.databaseBuilder(context.applicationContext,
            AsteroidsDatabase::class.java,
            "videos").build()
    }
    return INSTANCE
}