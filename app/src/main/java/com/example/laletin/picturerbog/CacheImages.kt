package com.example.laletin.picturerbog

import android.content.Context
import android.net.Uri
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.*


class CacheImages(private val ctx: Context) {

    companion object {
        private const val cacheSize = 4 * 1024 * 1024
        private const val fileCacheDirName = "PicturerBogCache"
        val cache = com.example.laletin.picturerbog.LruCache(cacheSize)
    }

    fun onLoad() {
        val file1 = ctx.cacheDir
        val file = File(file1, fileCacheDirName)
        try {
            ctx.openFileInput(file.name).bufferedReader().use {
                val count = it.readLine().toInt()
                for (i in 1..count) {
                    val key = it.readLine()
                    val bitmap = loadImageFromStorage("image_" + i.toString())
                    bitmap?.let { it1 ->
                        cache.setBitmapToMemory(key, it1)
                    }
                }
            }
        } catch (e: Exception) {
            return
        }
    }

    fun onSave() {
        val file1 = ctx.cacheDir
        val file = File(file1, fileCacheDirName)
        ctx.openFileOutput(file.name, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(cache.size().toString())
            it.newLine()
            val obj = cache.snapshot()
            var cnt = 0
            for (i in obj) {
                cnt++
                val f = i.key
                it.write(f)
                it.newLine()
                saveToInternalStorage(i.value, "image_" + cnt.toString())
            }
        }
    }


    private fun saveToInternalStorage(bitmapImage: Bitmap, filename: String) {
        val file = ctx.cacheDir
        val path = File(file, filename)

        try {
            ctx.openFileOutput(path.name, Context.MODE_PRIVATE).use {
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, it)
            }
            // Use the compress method on the BitMap object to write image to the OutputStream
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadImageFromStorage(filename: String): Bitmap? {

        val file = ctx.cacheDir
        val path = File(file, filename)

        try {
            ctx.openFileInput(path.name).use {
                val bitmap = BitmapFactory.decodeStream(it)
                return bitmap
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return null
        }

    }

    fun getTempFile(context: Context, url: String): File? =
            Uri.parse(url)?.lastPathSegment?.let { filename ->
                File.createTempFile(filename, null, context.cacheDir)
            }

}
