package com.example.laletin.picturerbog

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import kotlinx.android.parcel.Parcelize


import kotlinx.android.synthetic.main.image_item.view.*
import java.util.*

@Parcelize
data class Images(val id : String, val title: String, val url_s: Bitmap, val url_l : String, var bitmap_l : Bitmap?) : Parcelable


fun RecyclerView.setupForUsers(ctx: Context, resultImages: List<Images>, onItemClicked: (Images) -> Unit = {}) {
    layoutManager = LinearLayoutManager(ctx)
    adapter = UsersRecycler(resultImages, onItemClicked)
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

class UsersRecycler(private val users: List<Images>, private val onItemClicked: (Images) -> Unit) :
        RecyclerView.Adapter<ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) =
            ImageViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.image_item,
                            parent,
                            false
                    ) as FrameLayout
            ).apply {
                    frame.setOnClickListener { onItemClicked(users[adapterPosition]) }
            }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: ImageViewHolder, index: Int) {
        holder.image.setImageBitmap(users[index].url_s)
        holder.description.text = users[index].title
    }
}