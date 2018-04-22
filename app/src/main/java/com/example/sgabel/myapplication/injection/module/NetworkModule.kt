package com.example.sgabel.myapplication.injection.module

import android.app.Application
import com.example.sgabel.myapplication.utils.Constants
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule(var mBaseUrl: String) {

    @Provides
    @Singleton
    fun provideBaseUrl() = mBaseUrl

    @Provides
    @Singleton
    fun provideHttpCache(pApplication: Application): Cache {
        val vCacheSize: Long = 10 * 1024 * 1024
        return Cache(pApplication.cacheDir, vCacheSize)
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(pCache: Cache): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.cache(pCache)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(pGson: Gson, pOkHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(pGson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(mBaseUrl + Constants.mPath)
                .client(pOkHttpClient)
                .build()
    }
}