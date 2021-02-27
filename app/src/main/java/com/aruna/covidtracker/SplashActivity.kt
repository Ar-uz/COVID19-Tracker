package com.aruna.covidtracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aruna.covidtracker.main.AuthActivity
import com.aruna.covidtracker.main.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getSharedPreferences("user", MODE_PRIVATE)
        if(prefs.getString("name", null) != null) {
            println(prefs.getString("name", null))
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            println(prefs.getString("name", null))
            startActivity(Intent(this, AuthActivity::class.java))
        }
    }
}