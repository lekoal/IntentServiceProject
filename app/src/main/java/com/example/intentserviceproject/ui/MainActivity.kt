package com.example.intentserviceproject.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.intentserviceproject.databinding.ActivityMainBinding
import com.example.intentserviceproject.utils.systemintent.ACTION_SYSTEM_INTENT_SERVICE
import com.example.intentserviceproject.utils.systemintent.EXTRA_KEY_OUT
import com.example.intentserviceproject.utils.systemintent.SystemIntentService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var systemBroadcastReceiver: SystemBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val systemIntent = Intent(this, SystemIntentService::class.java)

        binding.systemIntentButton.setOnClickListener {
            binding.loadingProcessLayout.visibility = View.VISIBLE
            startService(
                systemIntent
                    .putExtra("time", 2000)
                    .putExtra("task", "Some action")
            )
            systemBroadcastReceiver = SystemBroadcastReceiver()
            val intentFilter = IntentFilter(ACTION_SYSTEM_INTENT_SERVICE)
            intentFilter.addCategory(Intent.CATEGORY_DEFAULT)
            registerReceiver(systemBroadcastReceiver, intentFilter)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(systemBroadcastReceiver)
    }

    inner class SystemBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val result = intent?.getStringExtra(EXTRA_KEY_OUT)
            binding.loadingProcessLayout.visibility = View.GONE
            Toast.makeText(
                this@MainActivity,
                "Process $result ended", Toast.LENGTH_SHORT
            )
                .show()
        }
    }
}