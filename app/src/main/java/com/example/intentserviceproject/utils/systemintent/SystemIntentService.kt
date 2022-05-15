package com.example.intentserviceproject.utils.systemintent

import android.app.IntentService
import android.content.Intent
import android.util.Log

class SystemIntentService : IntentService("name") {

    private val tag = "IntentServiceLog"

    @Deprecated("Deprecated in Java")
    override fun onCreate() {
        super.onCreate()
        Log.i(tag, "onCreate")
    }

    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(intent: Intent?) {
        val time = intent?.getIntExtra("time", 0)?.toLong()
        val label = intent?.getStringExtra("task")
        Log.i(tag, "onHandleIntent start: $label")
        try {
            if (time != null) {
                Thread.sleep(time)
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.i(tag, "onHandleIntent end: $label")
    }
}