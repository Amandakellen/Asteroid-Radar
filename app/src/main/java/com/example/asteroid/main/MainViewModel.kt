package com.example.asteroid.main

import android.util.Log
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
        // Observando a lista de asteroides no banco de dados
        getAsteroidsFromCache()
    }

    private fun getAsteroidsFromCache() {
        _isLoading.value = true
        asteroidRepository.getAllAsteroidsFromDatabase().observeForever { asteroidList ->
            // Verifique se o cache está vazio
            if (asteroidList.isNullOrEmpty()) {
                // Caso não haja dados no cache, busque da API
                fetchAsteroids()
            } else {
                // Caso contrário, apenas atribua os dados do cache
                _asteroids.value = asteroidList
                _isLoading.value = false
                Log.i("viewModel", "Cache carregado com sucesso")
            }
        }
    }

    private fun fetchAsteroids() {
        viewModelScope.launch {
            try {
                // Chama a API para buscar os asteroides
                asteroidRepository.getAsteroidsFromApi()

                // Após a chamada à API, recarregue os dados do banco de dados
                _asteroids.value = asteroidRepository.getAllAsteroidsFromDatabase().value

                Log.i("viewModel", "Dados carregados da API e armazenados no banco de dados")
            } catch (e: Exception) {
                // Tratar erros de rede ou outros erros
                Log.e("viewModel", "Erro ao carregar asteroides da API", e)
            } finally {
                // Atualiza o estado de carregamento
                _isLoading.value = false
            }
        }
    }
}
