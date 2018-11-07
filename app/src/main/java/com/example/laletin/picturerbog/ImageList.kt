package com.example.laletin.picturerbog

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.image_list_fragment.view.*
import android.os.AsyncTask
import android.support.v4.app.FragmentActivity
import android.util.Log
import java.lang.ref.WeakReference


class ImageList : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.image_list_fragment, container, false).also { inflatedView ->
        inflatedView.image_list_fragment.setupForUsers(context!!) { index ->
            if (activity?.findViewById<View>(R.id.fragment_content) != null) {
                try {
                    val start = MyTask(index, this, activity, context!!)
                    start.execute(1)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                try {
                    val start = MyTask(index, this, null, context!!)
                    start.execute(0)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}

class MyTask(val index: Int, private val imageL: ImageList, activity: FragmentActivity?, ctx: Context) : AsyncTask<Int, Void, Int>() {

    private val contextRef: WeakReference<Context>? = WeakReference(ctx)
    private val activityRef: WeakReference<FragmentActivity?> = WeakReference(activity)
    private lateinit var image: Images

    override fun doInBackground(vararg params: Int?): Int {
        try {

            val url_l = ImagesHolder.json?.photos?.photo?.get(index)?.url_l!!
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
            val title = ImagesHolder.json?.photos?.photo?.get(index)?.title ?: "hello"
            image = Images("0", title, url_l)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return params[0]!!
    }

    override fun onPostExecute(mode: Int) {
        try {
            if (mode == 0) {
                val ctx = contextRef?.get()
                imageL.startActivity(ctx?.createUserIntent(image))
            } else {
                val activity = activityRef.get()
                val transaction = activity!!.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_content, ImageDetailsFragment.newInstance(image))
                transaction.commit()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}