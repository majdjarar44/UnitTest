package com.example.unittestjunit.newtwork

import com.example.unittestjunit.remote.reponses.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixapayApi {

    @GET("/api/")
    suspend fun searchForImage(@Query("q") query: String, @Query("api_key") apiKey: String):Response<ImageResponse>
//   suspend fun getImages(@Query ("q")  query: String, @Query("api_key") apiKey:String = BuildCompat.)
}