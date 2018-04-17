package com.example.sgabel.myapplication.local

import android.content.SharedPreferences
import android.support.annotation.NonNull
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PreferencesHelper @Inject constructor(private var preferences: SharedPreferences) {

    fun putString(@NonNull key: String, @NonNull value: String) {
        preferences.edit().putString(key, value).apply();
    }

    fun getString(@NonNull key: String): String {
        return preferences.getString(key, "")
    }

    fun putBoolean(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun putInt(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    fun getInt(key: String): Int {
        return preferences.getInt(key, -1)
    }

    fun clear() {
        preferences.edit().clear().apply()
    }

}