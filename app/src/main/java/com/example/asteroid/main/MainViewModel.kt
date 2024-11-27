package com.example.asteroid.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asteroid.data.Asteroid
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
        getAsteroidsFromCache()
    }

    private fun getAsteroidsFromCache() {
        _isLoading.value = true
        asteroidRepository.getAllAsteroidsFromDatabase().observeForever { asteroidList ->
            if (asteroidList.isNullOrEmpty()) {
                fetchAsteroids()
            } else {
                _asteroids.value = asteroidList
                _isLoading.value = false
                Log.i("viewModel", "Cache carregado com sucesso")
            }
        }
    }

    fun getLastAsteroid(): Asteroid? {
        return _asteroids.value?.lastOrNull()
    }

    private fun fetchAsteroids() {
        viewModelScope.launch {
            try {
                asteroidRepository.getAsteroidsFromApi()
                _asteroids.value = asteroidRepository.getAllAsteroidsFromDatabase().value
                Log.i("viewModel", "Dados carregados da API e armazenados no banco de dados")
            } catch (e: Exception) {
                Log.e("viewModel", "Erro ao carregar asteroides da API", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
