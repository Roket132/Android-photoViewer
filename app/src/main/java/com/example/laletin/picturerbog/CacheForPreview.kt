package com.example.laletin.picturerbog

import android.graphics.Bitmap
import android.util.Log
import android.util.LruCache

class CacheForPreview {
    companion object {
        private const val cacheSize = 4 * 1024 * 1024
        val Cache = com.example.laletin.picturerbog.LruCacheF(cacheSize)
    }
}

class LruCacheF(maxSize: Int) : LruCache<String, Bitmap>(maxSize) {
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
