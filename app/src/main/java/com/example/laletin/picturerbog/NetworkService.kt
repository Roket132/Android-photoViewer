package com.example.laletin.picturerbog

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.regex.Pattern
import com.google.gson.GsonBuilder
import com.google.gson.Gson
import retrofit2.converter.scalars.ScalarsConverterFactory


class NetworkService private constructor() {
    private val mRetrofit: Retrofit

    val jsonApi: JSONFlickrHolderApi
        get() = mRetrofit.create<JSONFlickrHolderApi>(JSONFlickrHolderApi::class.java)

    init {
        val gson = GsonBuilder()
                .setLenient()
                .create()
        mRetrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
    }

    companion object {
        private var mInstance: NetworkService? = null
        private val BASE_URL = "https://api.flickr.com/services/rest/?safe_search=safe&api_key=4ed4d7bcac872286fa3687b953af1d42&sort=relevance&method=flickr.interestingness.getList&per_page=50&media=photos&extras=url_sq,url_t,url_s,url_q,url_m,url_n,url_z,url_c,url_l,url_o&license=1,2,3,4,5,6&format=json"

        val instance: NetworkService
            get() {
                if (mInstance == null) {
                    mInstance = NetworkService()
                }
                return mInstance as NetworkService
            }
    }
}
