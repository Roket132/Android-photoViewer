package com.example.laletin.picturerbog

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import kotlinx.android.parcel.Parcelize


import kotlinx.android.synthetic.main.image_item.view.*

@Parcelize
data class Images(val id: String, val title: String, val url_l: String) : Parcelable


fun RecyclerView.setupForUsers(ctx: Context, onItemClicked: (index: Int) -> Unit = {}) {
    layoutManager = LinearLayoutManager(ctx)
    adapter = UsersRecycler(onItemClicked)
    setHasFixedSize(true)

}

fun Context.createUserIntent(image: Images): Intent {
    val intent = Intent(this, DisplayImageActivity::class.java)
    intent.putExtra("IMAGE", image)
    return intent
}


class ImageViewHolder(val frame: FrameLayout) : RecyclerView.ViewHolder(frame) {
    var image = frame.image_item!!
    var description = frame.description!!
}

class UsersRecycler(private val onItemClicked: (index: Int) -> Unit) :
        RecyclerView.Adapter<ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) =
            ImageViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.image_item,
                            parent,
                            false
                    ) as FrameLayout
            ).apply {
                frame.setOnClickListener { onItemClicked(adapterPosition) }
            }

    override fun getItemCount() = 50

    override fun onBindViewHolder(holder: ImageViewHolder, index: Int) {
        DownloadPreviewTask(holder).execute(index)
    }
}