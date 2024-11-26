package com.example.asteroid

import android.app.Application
import com.example.asteroid.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class AsteroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Inicializa o Koin com o appModule
        startKoin {
            androidContext(this@AsteroidApplication)
            modules(appModule)
        }
    }
}
