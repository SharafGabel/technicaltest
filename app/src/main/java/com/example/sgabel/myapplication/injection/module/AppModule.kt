package com.example.sgabel.myapplication.injection.module

import android.app.Application
import dagger.Provides
import javax.inject.Singleton

class AppModule(var mApplication: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return mApplication;
    }

}