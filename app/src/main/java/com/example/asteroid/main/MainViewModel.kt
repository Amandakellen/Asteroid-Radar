package com.example.asteroid.main

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asteroid.Asteroid
import com.example.asteroid.database.getDatabase
import com.example.asteroid.repository.AsteroidRepository
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database =  getDatabase(application)
    private val repository = AsteroidRepository(database)

    //var playlist : LiveData<List<Asteroid>>

    init {
        viewModelScope.launch {
            repository.refreshAsteroids()
        }

        //playlist = repository.asteroids
    }
}
