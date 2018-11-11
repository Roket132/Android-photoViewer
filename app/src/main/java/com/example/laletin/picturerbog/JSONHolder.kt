package com.example.laletin.picturerbog

import com.fasterxml.jackson.databind.ObjectMapper
import java.net.URL
import java.util.regex.Pattern

class JSONHolder {
    companion object {
        private var json: JSONImages? = null
        private var downloaded = false
    }

    fun get(): JSONImages? {
        if (!downloaded) {
            json = downloadJSON()
            downloaded = true
        }
        return json
    }

    private fun downloadJSON(): JSONImages? {
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
