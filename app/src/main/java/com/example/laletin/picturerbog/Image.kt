package com.example.laletin.picturerbog

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ListAdapter
import com.fasterxml.jackson.databind.ObjectMapper
import com.squareup.picasso.Picasso
import kotlinx.android.parcel.Parcelize

import kotlinx.android.synthetic.main.image_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern
import kotlin.coroutines.experimental.coroutineContext


@Parcelize
data class Images(val id: String, val title: String, val urlL: String) : Parcelable

var adapter_ : RecyclerView.Adapter<*>? = null
var userQuerty : String = "ivan kayukoff"
var needUpdate = false

/**
 * Этот метод форматирует response от моего API
 */
private fun deserialize(responseString: String): String? {
    val p = Pattern.compile(".*?\\((.*)\\)$")
    val m = p.matcher(responseString)
    var json: String? = null
    if (m.matches()) {
        json = m.group(1)
    }
    return json
}

fun makeSearch(str: String) {
    userQuerty = str
    NetworkService.instance
            .jsonApi
            .getPostWithFormat(userQuerty)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>?, t: Throwable?) {
                    t?.printStackTrace()
                }
                override fun onResponse(call: Call<String>?, response: Response<String>?) {
                    /**
                     * Пришлось response принимать строкой, а потом её парсить в json.
                     * Почему?
                     * Мой API возвращат плохо отформатированную строку
                     * => вот это вот не работало -> .addConverterFactory(GsonConverterFactory.create(gson))
                     * А как добавить свой converter я не разобрался
                     */
                    val result = deserialize(response?.body()!!)
                    val mapper = ObjectMapper()
                    val json : JSONImages =  mapper.readValue(result, JSONImages::class.java)
                    JSONHolder().setJSON(json)
                    adapter_?.notifyDataSetChanged()
                }
            })
}

fun RecyclerView.setupForUsers(ctx: Context, onItemClicked: (index: Int) -> Unit = {}) {
    layoutManager = LinearLayoutManager(ctx)
    adapter = UsersRecycler(context!!, onItemClicked)
    adapter_ = adapter
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

class UsersRecycler(private val ctx: Context, private val onItemClicked: (index: Int) -> Unit) :
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

    override fun getItemCount(): Int = JSONHolder().get()?.photos?.photo?.size?: 0

    override fun onBindViewHolder(holder: ImageViewHolder, index: Int) {
        holder.image.setImageResource(R.drawable.ic_home_black_24dp)
        setPreviewOnHolder(holder, index)
    }

    private fun setPreviewOnHolder(holder: ImageViewHolder, index: Int) {
        try {
            holder.description.text = JSONHolder().get()?.photos?.photo?.get(index)?.title
            Picasso.with(ctx)
                    .load(JSONHolder().get()?.photos?.photo?.get(index)?.urlS)
                    .placeholder(R.drawable.ic_home_black_24dp)
                    .error(R.drawable.ic_home_black_24dp)
                    .into(holder.image)
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }
}

