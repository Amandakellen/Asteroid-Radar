package com.example.asteroid.database

import android.content.Context
import android.util.Log
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
    fun insertImage(vararg image: DatabasePictureDay){
        Log.d("a", "Inserting image data into database")
    }

    @Query("SELECT * FROM databasepictureday")
    fun getImage(): LiveData<DatabasePictureDay>

}

@Database(entities = [DatabaseAsteroid::class, DatabasePictureDay::class], version = 3)
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