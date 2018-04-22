package com.example.sgabel.myapplication.api

import com.example.sgabel.myapplication.Constants
import com.example.sgabel.myapplication.model.File
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming
import retrofit2.http.Url

class ApiManager {

    interface RestApi {

        @GET("{path}")
        fun getFiles(@Path(value = Constants.PATH, encoded = true) url: String): Call<List<File>>

        @Streaming
        @GET
        fun downloadFile(@Url pUrl: String?): Call<ResponseBody>
    }
}
