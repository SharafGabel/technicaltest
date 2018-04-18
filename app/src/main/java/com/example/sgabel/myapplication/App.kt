package com.example.sgabel.myapplication

import android.app.Application
import com.example.sgabel.myapplication.injection.component.DaggerNetworkComponent
import com.example.sgabel.myapplication.injection.component.NetworkComponent
import com.example.sgabel.myapplication.injection.module.AppModule
import com.example.sgabel.myapplication.injection.module.NetworkModule

class App : Application() {




    private val mNetworkComponent: NetworkComponent by lazy {
        DaggerNetworkComponent.builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule("http://private-2c2b2-nodes4.apiary-mock.com/nodes/"))
                .build();
    }


    override fun onCreate() {
        super.onCreate()

    }

    fun getNetworkComponent(): NetworkComponent {
        return mNetworkComponent
    }
}