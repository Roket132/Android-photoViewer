package com.example.laletin.picturerbog

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.image_list_fragment.*
import kotlinx.android.synthetic.main.image_list_fragment.view.*

class ImageList : Fragment() {

    var querty: String = ""

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.image_list_fragment, container, false).also { inflatedView ->
        inflatedView.image_list_fragment.setupForUsers(context!!) { index ->
            try {
                val title = JSONHolder().get()?.photos?.photo?.get(index)?.title
                val urlL = JSONHolder().get()?.photos?.photo?.get(index)?.urlL?: JSONHolder().get()?.photos?.photo?.get(index)?.urlS
                val image: Images = Images(index.toString(), title!!, urlL!!)
                if (activity?.findViewById<View>(R.id.fragment_content) != null) {
                    val transaction = activity!!.supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragment_content, ImageDetailsFragment.newInstance(image))
                    transaction.commit()
                } else {
                    startActivity(context!!.createUserIntent(image))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        textQuery.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                makeSearch(textQuery.text.toString())
                return@OnKeyListener true
            }
            false
        })
    }
}
