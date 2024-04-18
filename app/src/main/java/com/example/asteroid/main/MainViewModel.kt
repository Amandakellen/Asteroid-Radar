package com.example.asteroid.main

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.example.asteroid.Asteroid
import com.example.asteroid.data.PictureOfDay
import com.example.asteroid.database.getDatabase
import com.example.asteroid.repository.AsteroidRepository
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database =  getDatabase(application)
    private val repository = AsteroidRepository(database)

    var playlist : LiveData<List<Asteroid>>
    var pictureOfDay : LiveData<PictureOfDay>
    var urlImage: String?

    init {
        viewModelScope.launch {
            repository.refreshAsteroids()
            repository.refreshImageDay()

        }

        playlist = repository.asteroids
        pictureOfDay = repository.pictureOfDay

        urlImage = pictureOfDay.value?.url
    }

}
