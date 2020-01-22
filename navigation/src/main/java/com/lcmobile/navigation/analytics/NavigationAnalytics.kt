package com.lcmobile.navigation.analytics

import android.content.Context
import android.util.Log
import com.lcmobile.navigation.NavigationAnalytics

class NavigationAnalytics(
    private val context: Context
) {
    companion object {
        private const val TAG = "NavigationAnalytics"
    }

    fun log(analytics: NavigationAnalytics) {
        Log.d(TAG, analytics.data)
    }
}