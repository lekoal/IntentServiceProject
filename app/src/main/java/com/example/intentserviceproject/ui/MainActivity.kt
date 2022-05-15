package com.example.intentserviceproject.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.intentserviceproject.databinding.ActivityMainBinding
import com.example.intentserviceproject.utils.systemintent.SystemIntentService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val systemIntent = Intent(this, SystemIntentService::class.java)

        binding.systemIntentButton.setOnClickListener {
            startService(systemIntent.putExtra("time", 2000).putExtra("task", "Some action"))
        }
    }
}