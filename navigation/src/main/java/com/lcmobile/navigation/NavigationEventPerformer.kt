package com.lcmobile.navigation

import android.content.Intent
import android.net.Uri

class NavigationEventPerformer(
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
        }
    }
}