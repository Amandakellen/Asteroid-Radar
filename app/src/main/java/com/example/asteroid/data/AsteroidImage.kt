package com.example.asteroid.data

import androidx.annotation.DrawableRes
import com.example.asteroid.R

enum class AsteroidImage(@DrawableRes val imageResId: Int) {
    HAZARDOUS(R.drawable.asteroid_hazardous), // Imagem de asteroide perigoso
    SAFE(R.drawable.asteroid_safe)           // Imagem de asteroide seguro
}