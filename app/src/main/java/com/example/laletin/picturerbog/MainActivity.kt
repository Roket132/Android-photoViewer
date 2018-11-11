package com.example.laletin.picturerbog


import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import com.fasterxml.jackson.databind.ObjectMapper
import java.lang.ref.WeakReference

import java.net.URL
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    class DownloadJSON(ctx: MainActivity) : AsyncTask<Void, Void, JSONImages?>() {

        private val contextRef: WeakReference<MainActivity>? = WeakReference(ctx)

        override fun doInBackground(vararg params: Void): JSONImages? {
            try {
                //todo создать json
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
                return mapper.readValue(result, JSONImages::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: JSONImages?) {
            super.onPostExecute(result)
            JSONHolder.json = result
            val ctx = contextRef?.get()
            ctx?.setContentView(R.layout.activity_main)
        }

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

    val start = DownloadJSON(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        start.execute()
    }

    override fun onDestroy() {
        super.onDestroy()
        start.cancel(true)
    }

}


