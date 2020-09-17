package com.example.movieapp.movieapp.webservice

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService{

    @GET("/?")
    fun getSearchResult(@Query("s") searchParameter : String,@Query("apikey")apikey : String ="6f227ead",@Query("page")pageNo : Int = 1) : Call<ResponseBody>

}