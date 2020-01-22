package com.lcmobile.navigation

import android.content.Intent
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

interface NavigationHeaderProvider {
    fun onCreateNavigationHeader(root: ViewGroup): View?
}

interface NavigationInflaterProvider {
    fun onInflateNavigationItems(menu: Menu, items: List<NavigationItem>)
}

internal interface NavigationPerformerListener {
    fun onEventPerform()
    fun handleAnalytics(analytics: NavigationAnalytics)
    fun handleFragment(fragment: Fragment)
    fun handleActivity(intent: Intent)
}

