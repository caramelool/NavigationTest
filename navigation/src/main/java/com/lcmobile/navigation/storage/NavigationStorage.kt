package com.lcmobile.navigation.storage

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.lcmobile.navigation.Navigation
import java.io.BufferedReader

class NavigationStorage(
    context: Context
) {
    companion object {
        private const val TAG = "NavigationStorage"
        private const val NAME = "navigation_store"
        private const val KEY = "json"
        private const val DEFAULT_FILE = "navigation_default.json"
    }

    private val gson by lazy { Gson() }
    private val sharedPreferences by lazy {
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }
    private val default by lazy {
        context.assets.open(DEFAULT_FILE)
            .bufferedReader()
            .use(BufferedReader::readText)
            .let {
                gson.fromJson(it, Navigation::class.java)
            }
    }

    fun store(navigation: Navigation) {
        val json = gson.toJson(navigation)
        sharedPreferences.edit()
            .putString(KEY, json)
            .apply()
    }

    fun restore(): Navigation {
        sharedPreferences.getString(KEY, null)?.let {
            try {
                return gson.fromJson(it, Navigation::class.java)
            } catch (e: JsonSyntaxException) {
                Log.e(TAG, "restore navigation", e)
            }
        }
        return default
    }

    fun compare(navigation: Navigation) = navigation == restore()
}