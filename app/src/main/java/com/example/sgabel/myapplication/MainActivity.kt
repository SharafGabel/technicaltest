package com.example.sgabel.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.sgabel.myapplication.api.RestApi
import com.example.sgabel.myapplication.model.File
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mRetrofit: Retrofit //? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as App).getNetworkComponent().inject(this)



    }

    /***
     * TODO : Add Observable for knowing when call has been done
     */
    fun getAllFiles() {
        val vFiles = mRetrofit.create(RestApi::class.java).getFiles()

        vFiles.enqueue(
                object : Callback<List<File>> {

                    override fun onResponse(call: Call<List<File>>, response: Response<List<File>>) {
                        //Set the response to the textview
                        Toast.makeText(applicationContext, response.body().toString(), Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<List<File>>, t: Throwable) {
                        //Set the error to the textview
                        Toast.makeText(applicationContext, "error receiving data", Toast.LENGTH_LONG).show()
                    }
                });
    }
}
