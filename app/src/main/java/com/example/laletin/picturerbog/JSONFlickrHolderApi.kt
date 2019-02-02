package com.example.laletin.picturerbog

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


public interface JSONFlickrHolderApi {
    @GET("{empty}")
    fun getPostWithFormat(@Path("empty") format: String): Call<String>
}