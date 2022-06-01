package com.example.intentserviceproject.utils.customintent

import android.app.Service
import android.content.Intent
import android.os.*
import androidx.annotation.WorkerThread

abstract class MyIntentService(name: String) : Service() {

    private val mName = name
    private var mRedelivery = false
    @Volatile
    lateinit var mServiceLooper: Looper
    @Volatile
    lateinit var mServiceHandler: ServiceHandler

    inner class ServiceHandler(looper: Looper?) : Handler(looper!!) {
        override fun handleMessage(msg: Message) {
            onHandleIntent(msg.obj as Intent)
            stopSelf(msg.arg1)
            super.handleMessage(msg)
        }
    }

    override fun onCreate() {
        super.onCreate()

        val thread = HandlerThread("IntentService[$mName]")
        thread.start()

        mServiceLooper = thread.looper
        mServiceHandler = ServiceHandler(mServiceLooper)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val msg: Message = mServiceHandler.obtainMessage()
        msg.arg1 = startId
        msg.obj = intent
        mServiceHandler.sendMessage(msg)
        super.onStartCommand(intent, flags, startId)
        return if (mRedelivery) START_REDELIVER_INTENT
        else START_NOT_STICKY
    }

    override fun onDestroy() {
        mServiceLooper.quit()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @WorkerThread
    protected abstract fun onHandleIntent(intent: Intent?)
}