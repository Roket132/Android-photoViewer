package com.example.laletin.picturerbog

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import com.fasterxml.jackson.databind.ObjectMapper
import com.squareup.picasso.Picasso
import kotlinx.android.parcel.Parcelize

import kotlinx.android.synthetic.main.image_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


@Parcelize
data class Images(val id: String, val title: String, val urlL: String) : Parcelable

fun RecyclerView.setupForUsers(ctx: Context, onItemClicked: (index: Int) -> Unit = {}) {
    layoutManager = LinearLayoutManager(ctx)
    adapter = UsersRecycler(context!!, onItemClicked)
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

    override fun getItemCount() = 50

    override fun onBindViewHolder(holder: ImageViewHolder, index: Int) {
        holder.image.setImageResource(R.drawable.ic_home_black_24dp)
        val urlS = JSONHolder().get()?.photos?.photo?.get(index)?.urlS

        if (urlS == null) {
            NetworkService.instance
                    .jsonApi
                    .getPostWithFormat("")
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
                            setPreviewOnHolder(holder, index)
                        }
                    })
        } else {
            setPreviewOnHolder(holder, index)
        }
    }

    private fun setPreviewOnHolder(holder: ImageViewHolder, index: Int) {
        holder.description.text = JSONHolder().get()?.photos?.photo?.get(index)?.title
        Picasso.with(ctx)
                .load(JSONHolder().get()?.photos?.photo?.get(index)?.urlS)
                .placeholder(R.drawable.ic_home_black_24dp)
                .error(R.drawable.ic_home_black_24dp)
                .into(holder.image)
    }

    /**
     * Вот этот метод форматирует response от моего API
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
}

