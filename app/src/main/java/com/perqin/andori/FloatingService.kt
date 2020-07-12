package com.perqin.andori

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FloatingService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val ret = super.onStartCommand(intent, flags, startId)
        val notification = NotificationCompat.Builder(this, "floating")
            .setContentTitle("title")
            .setContentText("content")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .build()
        startForeground(1, notification)
        return ret
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