package com.lcmobile.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

internal interface NavigationFragmentFactory {
    fun instantiate(className: String, arg: Map<String, String>?): Fragment
}

internal class NavigationFragmentFactoryImpl(
    private val classLoader: ClassLoader
) : FragmentFactory(), NavigationFragmentFactory {
    override fun instantiate(className: String, arg: Map<String, String>?): Fragment {
        return instantiate(classLoader, className).apply {
            arguments = Bundle().apply {
                arg?.forEach {
                    putString(it.key, it.value)
                }
            }
        }
    }
}