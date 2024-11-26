package com.example.asteroid.di

import com.example.asteroid.api.AsteroidApi
import com.example.asteroid.repository.AsteroidRepository
import com.example.asteroid.main.MainViewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {

    // Definir o Retrofit
    single {
        Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AsteroidApi::class.java)
    }

    // Definir o repositório
    single { AsteroidRepository(get()) }

    // Definir o ViewModel
    viewModel { MainViewModel(get()) }
}
