package com.example.intentserviceproject.utils.customintent

import android.content.Intent
import com.example.intentserviceproject.utils.systemintent.EXTRA_KEY_OUT

const val ACTION_CUSTOM_INTENT_SERVICE = "com.example.intentserviceproject.CUSTOM.RESPONSE"

class CustomIntentService : MyIntentService("name") {

    private var extraOut = ""

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
        responseIntent.action = ACTION_CUSTOM_INTENT_SERVICE
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT)
        responseIntent.putExtra(EXTRA_KEY_OUT, extraOut)
        sendBroadcast(responseIntent)
    }
}