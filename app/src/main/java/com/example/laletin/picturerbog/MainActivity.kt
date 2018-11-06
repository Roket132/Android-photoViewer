package com.example.laletin.picturerbog


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import java.lang.ref.WeakReference

import java.net.URL
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    class DownloadPreview(ctx: MainActivity) : AsyncTask<Void, Void, Void?>() {

        private var resultImages = ArrayList<Images>()
        private val contextRef: WeakReference<MainActivity>? = WeakReference(ctx)

        override fun doInBackground(vararg params: Void): Void? {
            try {
                //todo создать list
                /**
                 * flickr.interestingness.getList
                 * flickr.photos.search
                 */
                val response = URL("https://api.flickr.com/services/rest/?safe_search=safe&api_key=4ed4d7bcac872286fa3687b953af1d42&sort=relevance&method=flickr.interestingness.getList&per_page=50&media=photos&extras=url_sq,url_t,url_s,url_q,url_m,url_n,url_z,url_c,url_l,url_o&license=1,2,3,4,5,6&format=json")
                        .openConnection().run {
                            connect()
                            getInputStream().bufferedReader().readLines().joinToString("");//.javaClass);
                        }
                val result = deserialize(response)
                val mapper = ObjectMapper()
                val images: JSONImages = mapper.readValue(result, JSONImages::class.java)

                for (i in 0 until 50) {
                    if (isCancelled)
                        return null
                    val urldisplay = images.photos?.photo?.get(i)?.url_s
                    var mIcon11: Bitmap? = null
                    if (CacheForPreview.Cache.get(urldisplay) == null) {
                        try {
                            val `in` = java.net.URL(urldisplay).openStream()
                            mIcon11 = BitmapFactory.decodeStream(`in`)
                            urldisplay?.let {
                                if (mIcon11 != null) {
                                    CacheForPreview.Cache.setBitmapToMemory(it, mIcon11!!)
                                }
                            }
                        } catch (e: Exception) {
                            Log.e("Error", e.message)
                            e.printStackTrace()
                        }
                    } else {
                        mIcon11 = CacheForPreview.Cache.get(urldisplay);
                    }
                    val url_s = mIcon11!!
                    val id = images.photos?.photo?.get(i)?.id ?: "228"
                    val title = images.photos?.photo?.get(i)?.title ?: "ojvp"
                    //fixme ojvp???
                    val url_m = images.photos?.photo?.get(i)?.url_m ?: "ojvp"
                    val url_l = images.photos?.photo?.get(i)?.url_l ?: urldisplay ?: "ojvp"
                    resultImages.add(Images(id, title, url_s, url_l, null))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            ImagesHolder.iamgesList = resultImages
            val ctx = contextRef?.get()
            ctx?.setContentView(R.layout.activity_main)
        }

        protected fun deserialize(responseString: String): String? {
            val p = Pattern.compile(".*?\\((.*)\\)$")
            val m = p.matcher(responseString)
            var json: String? = null
            if (m.matches()) {
                json = m.group(1)
            }

            return json
        }
    }

    val start = DownloadPreview(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        start.execute()
    }

    override fun onDestroy() {
        super.onDestroy()
        start.cancel(true)
    }

}


