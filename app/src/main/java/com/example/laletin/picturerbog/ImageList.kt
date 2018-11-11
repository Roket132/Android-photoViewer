package com.example.laletin.picturerbog

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.image_list_fragment.view.*
import android.os.AsyncTask
import android.os.IBinder
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.util.Log
import java.lang.ref.WeakReference


class ImageList : Fragment() {

    private var mService: DownloadImageService? = null
    private var mBound = false
    private var modeServerConnection = 0
    private var contextRef: WeakReference<Context>? = null//  WeakReference(ctx)
    private var activityRef: WeakReference<FragmentActivity?>? = null// WeakReference(activity)
    private var indexServerConnection: Int = 0

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.image_list_fragment, container, false).also { inflatedView ->
        inflatedView.image_list_fragment.setupForUsers(context!!) { index ->

            contextRef = WeakReference(context!!)
            indexServerConnection = index
            if (activity?.findViewById<View>(R.id.fragment_content) != null) {
                modeServerConnection = 2
                activityRef = WeakReference(activity)
            } else {
                modeServerConnection = 1
            }

            try {
                if (!mBound) {
                    val intent = Intent(context!!, DownloadImageService::class.java)
                    context!!.bindService(intent, DownloadImageConnection, 0)
                    context!!.startService(intent)
                } else {
                    mService?.downloadImage(modeServerConnection, index, this, activityRef, contextRef)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private val DownloadImageConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName,
                                        service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as DownloadImageService.LocalBinder
            mService = binder.service
            mService!!.downloadImage(modeServerConnection, indexServerConnection, this@ImageList, activityRef, contextRef)
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }
}
