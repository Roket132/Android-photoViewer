package com.example.laletin.picturerbog

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.support.v4.app.FragmentActivity
import android.widget.Toast
import java.lang.ref.WeakReference


class DownloadImageService : Service(), OnTaskCompleted {

    private val mBinder = LocalBinder()
    private var mServiceIsStarted: Boolean = false
    private var startId: Int = 0


    fun downloadImage(mode: Int, index: Int, imageL: ImageList,
                      activity: WeakReference<FragmentActivity?>?, ctx: WeakReference<Context>?) {

        val start = DownloadImageTask(index, imageL, activity, ctx, this)
        start.execute(mode)

    }

    override fun onTaskCompleted() {
        stopSelf(startId)
    }


    inner class LocalBinder : Binder() {
        internal// Return this instance of LocalService so clients can call public methods
        val service: DownloadImageService
            get() = this@DownloadImageService
    }

    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        mServiceIsStarted = true
        this.startId = startId

        return Service.START_STICKY
    }

    override fun onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }
}