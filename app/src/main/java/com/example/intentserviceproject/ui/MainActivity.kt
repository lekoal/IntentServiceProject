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
import com.example.intentserviceproject.utils.customintent.ACTION_CUSTOM_INTENT_SERVICE
import com.example.intentserviceproject.utils.customintent.CustomIntentService
import com.example.intentserviceproject.utils.systemintent.ACTION_SYSTEM_INTENT_SERVICE
import com.example.intentserviceproject.utils.systemintent.EXTRA_KEY_OUT
import com.example.intentserviceproject.utils.systemintent.SystemIntentService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var broadcastReceiver: MyBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val systemIntent = Intent(this, SystemIntentService::class.java)
        val customIntent = Intent(this, CustomIntentService::class.java)

        binding.systemIntentButton.setOnClickListener {
            val intentFilter = IntentFilter(ACTION_SYSTEM_INTENT_SERVICE)
            sendIntent(2001, "Some action with system intent", systemIntent, intentFilter)
        }

        binding.customIntentButton.setOnClickListener {
            val intentFilter = IntentFilter(ACTION_CUSTOM_INTENT_SERVICE)
            sendIntent(2000, "Some action with custom intent", customIntent, intentFilter)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

    private fun sendIntent(time: Int, task: String, intent: Intent, intentFilter: IntentFilter) {
        binding.loadingProcessLayout.visibility = View.VISIBLE
        broadcastReceiver = MyBroadcastReceiver()
        startService(
            intent
                .putExtra("time", time)
                .putExtra("task", task)
        )
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT)
        registerReceiver(broadcastReceiver, intentFilter)
    }

    inner class MyBroadcastReceiver : BroadcastReceiver() {
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
