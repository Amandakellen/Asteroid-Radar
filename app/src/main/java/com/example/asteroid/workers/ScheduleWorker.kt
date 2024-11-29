package com.example.asteroid.workers

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit


fun scheduleAsteroidDownloadWorker(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresCharging(true)
        .build()

    val workRequest = PeriodicWorkRequestBuilder<AsteroidDownloadWorker>(1, TimeUnit.DAYS)
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "AsteroidDownloadWorker",
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    )
}
