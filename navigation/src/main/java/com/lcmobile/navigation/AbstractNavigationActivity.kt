package com.lcmobile.navigation

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import kotlinx.android.synthetic.main.activity_navigation.*

abstract class AbstractNavigationActivity : AppCompatActivity(),
    NavigationPerformerListener, NavigationInflaterProvider, NavigationHeaderProvider {

    private val eventPerformer by lazy {
        val factory = NavigationFragmentFactoryImpl(classLoader)
        NavigationEventPerformer(factory, this)
    }

    private val navigationStore by lazy {
        NavigationStorage(this)
    }

    private val analyticsPerformer by lazy {
        NavigationAnalyticsPerformer()
    }

    private val navigationInflater by lazy {
        NavigationInflater(this)
    }

    private val drawerFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.drawerNavigation) as NavigationDrawerFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        setSupportActionBar(toolbar)

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        bottomNavigation.visibility = View.GONE

        navigationStore.restore()?.let {
            internalInflateNavigation(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateNavigationHeader(root: ViewGroup): View? {
        return null
    }

    override fun onInflateNavigationItems(menu: Menu, items: List<NavigationItem>) {
        navigationInflater.inflate(menu, items) {
            val perform = eventPerformer.perform(it)
            return@inflate if (menu is BottomNavigationMenu) {
                if (supportActionBar?.isShowing == false) {
                    supportActionBar?.show()
                }
                perform.not()
            } else {
                perform
            }
        }
    }

    override fun onEventPerform() {
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    override fun handleAnalytics(analytics: NavigationAnalytics) {
        analyticsPerformer.log(analytics)
    }

    override fun handleFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                fragment
            )
            .commit()
    }

    override fun handleActivity(intent: Intent) {
        startActivity(intent)
    }

    fun inflateNavigation(navigation: Navigation) {
        if (navigationStore.compare(navigation)) {
            return
        }
        internalInflateNavigation(navigation)
        navigationStore.store(navigation)
    }

    private fun internalInflateNavigation(navigation: Navigation) {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        bottomNavigation.visibility = View.GONE
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        when (navigation.type) {
            NavigationType.Drawer -> inflateDrawerNavigation(navigation.items, navigation.subItems)
            NavigationType.Bottom -> inflateBottomNavigation(navigation.items, navigation.subItems)
            NavigationType.Both -> {
                inflateBottomNavigation(navigation.items)
                inflateDrawerNavigation(navigation.subItems)
            }
        }
        eventPerformer.perform(navigation.items.first().event)
    }

    private fun inflateDrawerNavigation(
        items: List<NavigationItem>,
        subItems: List<NavigationItem> = listOf()
    ) {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        val drawable = ContextCompat.getDrawable(
            this,
            R.drawable.ic_menu_white
        )
        supportActionBar?.setHomeAsUpIndicator(drawable)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val list = if (subItems.isNotEmpty()) {
            listOf(*items.toTypedArray(), *subItems.toTypedArray())
        } else {
            items
        }
        drawerFragment.inflate(list)
    }

    private fun inflateBottomNavigation(
        items: List<NavigationItem>,
        subItems: List<NavigationItem> = listOf()
    ) {
        bottomNavigation.menu.apply {
            clear()
            onInflateNavigationItems(this, items)
            if (subItems.isNotEmpty()) {
                inflateMenuInBottomNavigation(subItems)
            }
        }
        bottomNavigation.visibility = View.VISIBLE
    }

    private fun inflateMenuInBottomNavigation(items: List<NavigationItem>) {
        bottomNavigation.menu.add(1, -99, 1, "Menu")
            .setIcon(R.drawable.ic_menu_black)
            .setCheckable(true)
            .setOnMenuItemClickListener {
                val fragment = NavigationDrawerFragment.newInstance(items)
                handleFragment(fragment)
                supportActionBar?.hide()
                false
            }
    }
}