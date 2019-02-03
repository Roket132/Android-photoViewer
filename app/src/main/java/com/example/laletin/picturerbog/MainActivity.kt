package com.example.laletin.picturerbog

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.image_item.*
import kotlinx.android.synthetic.main.image_list_fragment.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // favoritesButton!!.setOnClickListener(favOnClick)
    }
}
