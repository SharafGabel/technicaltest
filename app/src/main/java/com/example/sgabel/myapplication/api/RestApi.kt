package com.example.sgabel.myapplication.api

import com.example.sgabel.myapplication.model.File
import retrofit2.Call
import retrofit2.http.GET

interface RestApi {

    @GET("/nodes")
    fun getFiles(): Call<List<File>>
}