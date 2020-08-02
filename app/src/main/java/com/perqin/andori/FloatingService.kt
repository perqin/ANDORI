package com.perqin.andori

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.perqin.andori.ui.notification.NOTIFICATION_CHANNEL_ID_FLOATING
import com.perqin.andori.ui.notification.NOTIFICATION_ID_FLOATING
import com.perqin.andori.ui.pages.station.StationActivity
import com.perqin.andori.ui.shortcuts.SHORTCUT_CATEGORY_FLOATING
import com.perqin.andori.ui.shortcuts.SHORTCUT_ID_FLOATING
import com.perqin.andori.ui.shortcuts.rinkoPerson

class FloatingService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val ret = super.onStartCommand(intent, flags, startId)
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID_FLOATING)
            .setStyle(NotificationCompat.MessagingStyle(rinkoPerson)
                .addMessage(getString(R.string.text_youAreInTheStationNow), System.currentTimeMillis(), rinkoPerson))
            .setShortcutId(SHORTCUT_ID_FLOATING)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round))
            .setContentTitle(rinkoPerson.name)
            .setContentText(getString(R.string.text_youAreInTheStationNow))
            .setOngoing(true)
            .setBubbleMetadata(NotificationCompat.BubbleMetadata.Builder()
                .setIcon(IconCompat.createWithResource(this, R.mipmap.ic_launcher_round))
                .setSuppressNotification(true)
                .setIntent(PendingIntent.getActivity(this, 0,
                    Intent(this, StationActivity::class.java), 0))
                .build())
            .build()
        NotificationManagerCompat.from(this).notify(NOTIFICATION_ID_FLOATING, notification)
        return ret
    }

    override fun onDestroy() {
        super.onDestroy()
        NotificationManagerCompat.from(this).cancel(NOTIFICATION_ID_FLOATING)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    companion object {
        private val isEnabled = MutableLiveData<Boolean>().apply {
            value = false
        }
        fun isEnabled(): LiveData<Boolean> = isEnabled

        fun start(context: Context) {
            if (isEnabled.value!!) {
                return
            }
            // Publish dynamic shortcuts
            ShortcutManagerCompat.addDynamicShortcuts(context, listOf(
                ShortcutInfoCompat.Builder(context, SHORTCUT_ID_FLOATING)
                    .setShortLabel(context.getString(R.string.label_gotoStation))
                    .setLongLabel(context.getString(R.string.label_gotoStation))
                    .setIcon(IconCompat.createWithResource(context, R.mipmap.ic_launcher_round))
                    .setCategories(setOf(SHORTCUT_CATEGORY_FLOATING))
                    .setIntent(Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://andori.perqin.com/floating")))
                    .setPerson(rinkoPerson)
                    .setLongLived(true)
                    .build()
            ))
            context.startService(Intent(context, FloatingService::class.java))
            isEnabled.value = true
        }

        fun stop(context: Context) {
            if (!isEnabled.value!!) {
                return
            }
            context.stopService(Intent(context, FloatingService::class.java))
            isEnabled.value = false
        }
    }
}