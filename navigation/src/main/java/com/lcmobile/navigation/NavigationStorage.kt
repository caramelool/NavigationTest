package com.lcmobile.navigation

import android.content.Context
import com.google.gson.Gson

class NavigationStorage(
    context: Context
) {
    companion object {
        private const val NAME = "navigation_store"
        private const val KEY = "json"
    }

    private val gson by lazy { Gson() }
    private val sharedPreferences by lazy {
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

    fun store(navigation: Navigation) {
        val json = gson.toJson(navigation)
        sharedPreferences.edit()
            .putString(KEY, json)
            .apply()
    }

    fun restore(): Navigation? {
        return sharedPreferences.getString(KEY, null)?.let {
            gson.fromJson(it, Navigation::class.java)
        }
    }

    fun compare(navigation: Navigation) = navigation == restore()
}