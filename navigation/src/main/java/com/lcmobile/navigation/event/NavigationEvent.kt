package com.lcmobile.navigation.event

import android.content.Intent
import android.net.Uri
import com.lcmobile.navigation.NavigationEvent
import com.lcmobile.navigation.NavigationEventType
import com.lcmobile.navigation.NavigationFragmentFactory
import com.lcmobile.navigation.NavigationPerformerListener

internal class NavigationEvent(
    private val fragmentFactory: NavigationFragmentFactory,
    private val listener: NavigationPerformerListener
) {
    fun perform(event: NavigationEvent): Boolean {
        listener.onEventPerform()
        return when (event.type) {
            NavigationEventType.Fragment -> {
                val fragment = fragmentFactory.instantiate(event.data, event.extra)
                listener.handleFragment(fragment)
                true
            }
            NavigationEventType.Deeplink -> {
                Intent(Intent.ACTION_VIEW).run {
                    data = Uri.parse(event.data)
                    event.extra?.forEach {
                        putExtra(it.key, it.value)
                    }
                    listener.handleActivity(this)
                }
                false
            }
        }.also {
            event.analytics?.let(listener::handleAnalytics)
        }
    }
}