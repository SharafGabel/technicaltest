package com.example.sgabel.myapplication.injection.module

import android.app.Application
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule(var mBaseUrl: String) {
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
                .baseUrl(mBaseUrl)
                .client(pOkHttpClient)
                .build()
    }
}