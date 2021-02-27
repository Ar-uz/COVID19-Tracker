package com.aruna.covidtracker.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aruna.covidtracker.databinding.ActivityAuthBinding
import com.google.android.material.snackbar.Snackbar

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btRegister.setOnClickListener {
            val name = binding.tilName.editText?.text.toString()
            val mob = binding.tilMob.editText?.text.toString()
            val age = binding.tilAge.editText?.text.toString()
            if(name.isNotBlank() && mob.isNotBlank() && age.isNotBlank()) {
                val prefs = getSharedPreferences("user", MODE_PRIVATE)
                with(prefs.edit()) {
                    putString("name", name)
                    putString("mob", mob)
                    putString("age", age)
                    apply()
                }
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Snackbar.make(binding.root, "All fields are necessary", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}