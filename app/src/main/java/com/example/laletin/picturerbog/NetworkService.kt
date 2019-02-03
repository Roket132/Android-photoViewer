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
        mRetrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
    }

    companion object {
        private var mInstance: NetworkService? = null
        private val BASE_URL = "https://api.flickr.com/services/"

        val instance: NetworkService
            get() {
                if (mInstance == null) {
                    mInstance = NetworkService()
                }
                return mInstance as NetworkService
            }
    }
}
