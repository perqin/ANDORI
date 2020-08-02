package com.perqin.andori

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.perqin.andori.ui.notification.NOTIFICATION_CHANNEL_ID_FLOATING

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
            NotificationChannel(
                NOTIFICATION_CHANNEL_ID_FLOATING,
                getString(R.string.label_floatingBubbles),
                NotificationManager.IMPORTANCE_DEFAULT,
            )
        ))
    }
}
