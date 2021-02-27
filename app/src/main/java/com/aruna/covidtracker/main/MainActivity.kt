package com.aruna.covidtracker.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.aruna.covidtracker.R
import com.aruna.covidtracker.databinding.ActivityMainBinding
import com.aruna.covidtracker.tracking.TrackingService

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.fl_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        startService(Intent(this, TrackingService::class.java))
        setupNotification()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fl_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                "100",
                "Realtime COVID-19 updates",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            serviceChannel.setSound(null, null)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

}