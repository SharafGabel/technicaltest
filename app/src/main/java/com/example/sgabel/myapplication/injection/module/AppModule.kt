package com.example.sgabel.myapplication.injection.module

import android.app.Application
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(var mApplication: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = mApplication

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

}