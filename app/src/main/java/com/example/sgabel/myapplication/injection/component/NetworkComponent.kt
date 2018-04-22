package com.example.sgabel.myapplication.injection.component

import com.example.sgabel.myapplication.activity.FileExplorerActivity
import com.example.sgabel.myapplication.activity.MediaActivity
import com.example.sgabel.myapplication.injection.module.AppModule
import com.example.sgabel.myapplication.injection.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class))
interface NetworkComponent {

    fun inject(pActivity: FileExplorerActivity)

    fun inject(pMediaActivity: MediaActivity)
}