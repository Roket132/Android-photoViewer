package com.example.laletin.picturerbog

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
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
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.image_list_fragment, container, false).also { inflatedView ->
        inflatedView.image_list_fragment.setupForUsers(context!!) { index ->
            try {
                val title = JSONHolder().get()?.photos?.photo?.get(index)?.title
                val urlL = JSONHolder().get()?.photos?.photo?.get(index)?.urlL
                val image: Images = Images(index.toString(), title!!, urlL!!)
                if (activity?.findViewById<View>(R.id.fragment_content) != null) {
                    val transaction = activity!!.supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragment_content, ImageDetailsFragment.newInstance(image))
                    transaction.commit()
                } else {
                    startActivity(context!!.createUserIntent(image))
                }
            }catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }
}
