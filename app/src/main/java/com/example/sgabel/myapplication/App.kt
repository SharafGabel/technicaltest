package com.example.sgabel.myapplication

import android.app.Application
import com.example.sgabel.myapplication.injection.component.DaggerNetworkComponent
import com.example.sgabel.myapplication.injection.component.NetworkComponent
import com.example.sgabel.myapplication.injection.module.AppModule
import com.example.sgabel.myapplication.injection.module.NetworkModule

class App() : Application() {
    private val mNetworkComponent: NetworkComponent by lazy {
        DaggerNetworkComponent.builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule("http://www.google.com"))
                .build();
    }


    override fun onCreate() {
        super.onCreate()
        mNetworkComponent.inject(this)
    }

    fun getNetworkComponent(): NetworkComponent {
        return mNetworkComponent
    }
}