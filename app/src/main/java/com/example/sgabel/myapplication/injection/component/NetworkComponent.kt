package com.example.sgabel.myapplication.injection.component

import com.example.sgabel.myapplication.MainActivity
import com.example.sgabel.myapplication.injection.module.AppModule
import com.example.sgabel.myapplication.injection.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class))
interface NetworkComponent {

    fun inject(pActivity: MainActivity)
}