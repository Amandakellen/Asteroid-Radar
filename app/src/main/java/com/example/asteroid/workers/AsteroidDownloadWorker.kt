package com.example.asteroid.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.asteroid.repository.AsteroidRepository


class AsteroidDownloadWorker(
    context: Context,
    workerParams: WorkerParameters,
    val asteroidRepository: AsteroidRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            asteroidRepository.getAsteroidsFromApi()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}