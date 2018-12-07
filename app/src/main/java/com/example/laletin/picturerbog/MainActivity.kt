package com.example.laletin.picturerbog

import android.os.Bundle
import AsyncLoad;
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val cacheImages = CacheImages(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AsyncLoad(cacheImages).execute()
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        super.onDestroy()
        cacheImages.onSave()
    }

}
