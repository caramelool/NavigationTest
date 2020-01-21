package com.lcmobile.navigation

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ColorStateListInflaterCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_navigation_drawer.*

class NavigationDrawerFragment : Fragment() {

    private var inflaterProvider: NavigationInflaterProvider? = null
    private var headerProvider: NavigationHeaderProvider? = null

    companion object {
        private const val EXTRA_ITEMS = "items"
        fun newInstance(items: List<NavigationItem>) =
            NavigationDrawerFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(EXTRA_ITEMS, arrayListOf(*items.toTypedArray()))
                }
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inflaterProvider = context as? NavigationInflaterProvider
        headerProvider = context as? NavigationHeaderProvider
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_navigation_drawer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelableArrayList<NavigationItem>(EXTRA_ITEMS)?.let {
            inflate(it.toList())
        }
        addHeaderView()
    }

    private fun addHeaderView() {
        headerProvider?.onCreateNavigationHeader(navigationView)?.let {
            if (navigationView.headerCount > 0) {
                val header = navigationView.getHeaderView(0)
                navigationView.removeHeaderView(header)
            }
            navigationView.addHeaderView(it)
        }
    }

    override fun onDetach() {
        super.onDetach()
        inflaterProvider = null
        headerProvider = null
    }

    fun inflate(items: List<NavigationItem>) {
        navigationView.menu.apply {
            clear()
            inflaterProvider?.onInflateNavigationItems(this, items)
        }
    }

}