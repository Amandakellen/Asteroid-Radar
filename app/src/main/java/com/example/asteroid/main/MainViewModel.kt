package com.example.asteroid.main

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.asteroid.data.Asteroid
import com.example.asteroid.data.AsteroidFilter
import com.example.asteroid.repository.AsteroidRepository
import kotlinx.coroutines.launch

class MainViewModel(private val asteroidRepository: AsteroidRepository) : ViewModel() {

    private val _asteroids = MutableLiveData<List<Asteroid>>()

    // MutableLiveData para o filtro
    private val _filter = MutableLiveData<AsteroidFilter>(AsteroidFilter.ALL)
    val filter: LiveData<AsteroidFilter> get() = _filter

    val asteroids: LiveData<List<Asteroid>> = _asteroids

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _pictureOfDayUrl = MutableLiveData<String>()
    val pictureOfDayUrl: LiveData<String> get() = _pictureOfDayUrl

    init {
        getAsteroidsFromCache()
        getImageDay()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setFilter(filter: AsteroidFilter) {
        _filter.value = filter
        fetchFilteredAsteroids(filter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchFilteredAsteroids(filter: AsteroidFilter) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                asteroidRepository.getFilteredAsteroids(filter).observeForever { asteroidList ->
                    _asteroids.value = asteroidList
                    _isLoading.value = false
                    Log.i("viewModel", "Asteroides filtrados com sucesso")
                }
            } catch (e: Exception) {
                _isLoading.value = false
                Log.e("viewModel", "Erro ao carregar asteroides filtrados", e)
            }
        }
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

    private fun getImageDay() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val picture = asteroidRepository.getImageDay()
                _pictureOfDayUrl.value = picture?.url
                Log.i("imageViewModel", "Imagem carregada com sucesso ${_pictureOfDayUrl.value}")
            } catch (e: Exception) {
                Log.e("imageViewModel", "Erro ao carregar imagem da API", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    val imageDescription = liveData {
        emit("Imagem da NASA de hoje")
    }
}
