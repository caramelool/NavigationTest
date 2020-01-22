package com.lcmobile.navigationtest

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.lcmobile.navigation.AbstractNavigationActivity


class MainActivity : AbstractNavigationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        request()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.refresh) {
            request()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateNavigationHeader(root: ViewGroup): View? {
        return layoutInflater.inflate(R.layout.nav_header, root, false)
    }

    private fun request() {
        getNavigation {
            inflateNavigation(it)
        }
    }
}