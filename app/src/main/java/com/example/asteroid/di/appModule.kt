package com.example.asteroid.di

import androidx.room.Room
import com.example.asteroid.Constants.BASE_URL
import com.example.asteroid.api.AsteroidApi
import com.example.asteroid.database.AsteroidDatabase
import com.example.asteroid.repository.AsteroidRepository
import com.example.asteroid.main.MainViewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AsteroidApi::class.java)
    }

    single { AsteroidRepository(get(),get()) }

    viewModel { MainViewModel(get()) }

    single {
        Room.databaseBuilder(
            get(),
            AsteroidDatabase::class.java,
            "asteroids_database"
        ).build()
    }

    single { get<AsteroidDatabase>().asteroidDao}

}
