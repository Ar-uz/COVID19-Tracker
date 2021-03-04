package com.aruna.covidtracker.tracking

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.aruna.covidtracker.R

class TrackingService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = NotificationCompat.Builder(this, "100")
            .setContentTitle("COVID-19 Tracker")
            .setContentText("Cases nearby (500m): 34")
            .setSmallIcon(R.drawable.ic_unsafe)
            .setSound(null)
            .setDefaults(0)
            .build()
        startForeground(1, notification)
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

}