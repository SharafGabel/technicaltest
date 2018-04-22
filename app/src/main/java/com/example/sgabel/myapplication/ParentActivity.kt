package com.example.sgabel.myapplication

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import retrofit2.Retrofit
import javax.inject.Inject

open class ParentActivity : AppCompatActivity() {

    @Inject
    lateinit var mRetrofit: Retrofit

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


}