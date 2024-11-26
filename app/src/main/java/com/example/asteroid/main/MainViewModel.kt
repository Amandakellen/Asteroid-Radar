package com.example.asteroid.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asteroid.Asteroid
import com.example.asteroid.repository.AsteroidRepository
import kotlinx.coroutines.launch

class MainViewModel(private val asteroidRepository: AsteroidRepository) : ViewModel() {

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>> get() = _asteroids

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _pictureOfDayUrl = MutableLiveData<String>()
    val pictureOfDayUrl: LiveData<String> get() = _pictureOfDayUrl

    init {
        fetchAsteroids()
    }

    private fun fetchAsteroids() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val asteroidList = asteroidRepository.getAsteroidsFromApi()
                _asteroids.value = asteroidList
            } catch (e: Exception) {
                // Tratar erros de rede
            } finally {
                _isLoading.value = false
            }
        }
    }
}
