package com.example.intentserviceproject.utils.systemintent

import android.app.IntentService
import android.content.Intent

const val EXTRA_KEY_OUT = "EXTRA_OUT"
const val ACTION_SYSTEM_INTENT_SERVICE = "com.example.intentserviceproject.SYSTEM.RESPONSE"

class SystemIntentService : IntentService("name") {

    private var extraOut = ""

    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(intent: Intent?) {
        val time = intent?.getIntExtra("time", 0)?.toLong()
        extraOut = intent?.getStringExtra("task").toString()

        try {
            if (time != null) {
                Thread.sleep(time)
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        val responseIntent = Intent()
        responseIntent.action = ACTION_SYSTEM_INTENT_SERVICE
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT)
        responseIntent.putExtra(EXTRA_KEY_OUT, extraOut)
        sendBroadcast(responseIntent)
    }
}