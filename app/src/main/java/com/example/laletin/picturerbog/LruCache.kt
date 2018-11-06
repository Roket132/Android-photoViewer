package com.example.laletin.picturerbog

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.LruCache

class LruCacheOgject {
    companion object {
        private const val cacheSize = 4 * 1024 * 1024
        val Cache = com.example.laletin.picturerbog.LruCache(cacheSize)
    }
}

class LruCache(maxSize: Int) :  LruCache<String, Bitmap>(maxSize){
    fun getBitmapFromMemory(key: String): Bitmap? {
        return this.get(key)
    }

    fun setBitmapToMemory(key: String, drawable: Bitmap) {
        if (getBitmapFromMemory(key) == null) {
            this.put(key, drawable)
            Log.d("TEST", "$key добавлен в кэш")
        }
    }
}

fun drawableToBitmap(drawable: Drawable): Bitmap? {
    var bitmap: Bitmap? = null

    if (drawable is BitmapDrawable) {
        if (drawable.bitmap != null) {
            return drawable.bitmap
        }
    }

    if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
        bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) // Single color bitmap will be created of 1x1 pixel
    } else {
        bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    }

    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
    drawable.draw(canvas)
    return bitmap
}