package com.perqin.andori

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannels()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannels() {
        NotificationManagerCompat.from(this).createNotificationChannels(listOf(
            NotificationChannel("floating", "Floating bubbles", NotificationManager.IMPORTANCE_DEFAULT)
        ))
    }
}
