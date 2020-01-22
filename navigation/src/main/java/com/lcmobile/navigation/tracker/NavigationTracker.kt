package com.lcmobile.navigation.tracker

import android.content.Context
import android.util.Log
import com.lcmobile.navigation.NavigationAnalytics

internal class NavigationTracker(
    private val context: Context
) {
    companion object {
        private const val TAG = "NavigationTracker"
    }

    fun track(analytics: NavigationAnalytics) {
        Log.d(TAG, analytics.data)
    }
}