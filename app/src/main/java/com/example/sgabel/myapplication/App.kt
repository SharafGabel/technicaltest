package com.example.sgabel.myapplication

import android.app.Application
import com.example.sgabel.myapplication.injection.component.DaggerNetworkComponent
import com.example.sgabel.myapplication.injection.component.NetworkComponent
import com.example.sgabel.myapplication.injection.module.AppModule
import com.example.sgabel.myapplication.injection.module.NetworkModule
import com.example.sgabel.myapplication.utils.Constants

class App : Application() {

    private val mNetworkComponent: NetworkComponent by lazy {
        DaggerNetworkComponent.builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule(Constants.mBaseUrl + Constants.mPath))
                .build()
    }


    override fun onCreate() {
        super.onCreate()

    }

    fun getNetworkComponent(): NetworkComponent {
        return mNetworkComponent
    }
}