package com.example.laletin.picturerbog;

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.abc_activity_chooser_view.*
import kotlinx.android.synthetic.main.abc_dialog_title_material.view.*
import kotlinx.android.synthetic.main.content_main.*

class DisplayImageActivity : AppCompatActivity(), FragmentWithText.FragmentWithTextListener {

    var title: String = ""
    var url: String = ""

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                navigateHome()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                navigateOther(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                navigateOther(2)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onTextClicked() {
        supportFragmentManager.popBackStack()
    }

    private fun navigateOther(index: Int) {
        supportFragmentManager.beginTransaction().apply {
            if (index == 1) {
                replace(R.id.detail_view_activity_frame, FragmentWithText.newInstance(title))
            } else {
                replace(R.id.detail_view_activity_frame, FragmentWithText.newInstance(url))
            }
            popOtherIfPresent()
            addToBackStack(null)
            commit()
        }
    }

    private fun popOtherIfPresent() {
        val stackCount = supportFragmentManager.backStackEntryCount
        if (stackCount == 0) {
            return
        }
        if (supportFragmentManager.getBackStackEntryAt(stackCount - 1).name != "TAG_HOME") supportFragmentManager.popBackStack()
    }

    private fun navigateHome() {
        popOtherIfPresent()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)

        val image = intent?.getParcelableExtra<Images>("IMAGE") ?: return
        if (supportFragmentManager.backStackEntryCount > 0) return
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.detail_view_activity_frame, ImageDetailsFragment.newInstance(image), "TAG_HOME")
            commit()
        }
        title = image.title
        url = image.urlL
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
