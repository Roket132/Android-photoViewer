package com.example.laletin.picturerbog

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

public interface JSONFlickrHolderApi {
    @GET("rest/?safe_search=safe&api_key=4ed4d7bcac872286fa3687b953af1d42&sort=relevance&method=flickr.photos.search&per_page=50&media=photos&extras=url_sq,url_t,url_s,url_q,url_m,url_n,url_z,url_c,url_l,url_o&license=1,2,3,4,5,6&format=json")
    fun getPostWithFormat(@Query("tags") userTag: String): Call<String>
}