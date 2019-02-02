package com.example.laletin.picturerbog

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log


interface onPreviewDownloaded {
    fun onPreviewDownloaded()
}

class DownloadPreviewTask(private val holder: ImageViewHolder) : AsyncTask<Int, Void, Pair<Bitmap?, String?>?>() {
    override fun onPreExecute() {
        super.onPreExecute()
        holder.image.setImageResource(R.drawable.ic_home_black_24dp)
    }

    override fun doInBackground(vararg params: Int?): Pair<Bitmap?, String?>? {
        try {
            val index = params[0] ?: return null
            val url_s = JSONHolder().get()?.photos?.photo?.get(index)?.url_s
            var mIcon11: Bitmap? = null

            if (CacheForPreview.Cache.get(url_s) == null) {
                try {
                    val `in` = java.net.URL(url_s).openStream()
                    mIcon11 = BitmapFactory.decodeStream(`in`)
                    url_s?.let {
                        if (mIcon11 != null) {
                            CacheForPreview.Cache.setBitmapToMemory(it, mIcon11!!)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("Error", e.message)
                    e.printStackTrace()
                }
            } else {
                mIcon11 = CacheForPreview.Cache.get(url_s)
            }
            return Pair(mIcon11, JSONHolder().get()?.photos?.photo?.get(index)?.title)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    override fun onPostExecute(result: Pair<Bitmap?, String?>?) {
        super.onPostExecute(result)
        holder.image.setImageBitmap(result?.first)
        holder.description.text = result?.second
    }
}