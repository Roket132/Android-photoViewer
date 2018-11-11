package com.example.laletin.picturerbog

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.support.v4.app.FragmentActivity
import android.util.Log
import java.lang.ref.WeakReference


interface OnTaskCompleted {
    fun onTaskCompleted()
}


class DownloadImageTask(val index: Int, private val imageL: ImageList,
                        private val activityRef: WeakReference<FragmentActivity?>?,
                        private val contextRef: WeakReference<Context>?,
                        private var listener: OnTaskCompleted) : AsyncTask<Int, Void, Int>() {

    private lateinit var image: Images

    override fun doInBackground(vararg params: Int?): Int {
        try {
            val url_l = JSONHolder.json?.photos?.photo?.get(index)?.url_l!!
            if (LruCacheOgject.Cache.get(url_l) == null) {
                var mIcon11: Bitmap? = null

                try {
                    val `in` = java.net.URL(url_l).openStream()
                    mIcon11 = BitmapFactory.decodeStream(`in`)
                } catch (e: Exception) {
                    Log.e("Error", e.message)
                    e.printStackTrace()
                }
                if (mIcon11 != null) {
                    LruCacheOgject.Cache.setBitmapToMemory(url_l, mIcon11)
                }
            }
            val title = JSONHolder.json?.photos?.photo?.get(index)?.title ?: "hello"
            image = Images("0", title, url_l)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return params[0]!!
    }

    override fun onPostExecute(mode: Int) {
        try {
            if (mode == 1) {
                val ctx = contextRef?.get()
                imageL.startActivity(ctx?.createUserIntent(image))
            } else if (mode == 2) {
                val activity = activityRef?.get()
                val transaction = activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.fragment_content, ImageDetailsFragment.newInstance(image))
                transaction?.commit()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        listener.onTaskCompleted()
    }
}