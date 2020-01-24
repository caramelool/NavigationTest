package com.lcmobile.navigation.inflater

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.lcmobile.navigation.NavigationEvent
import com.lcmobile.navigation.NavigationItem
import com.lcmobile.navigation.R

internal class NavigationInflater(
    private val context: Context
) {

    fun inflate(
        menu: Menu,
        items: List<NavigationItem>,
        block: (NavigationEvent) -> Boolean
    ) {
        items.forEach { item ->
            with(item) {
                menu.add(group, itemId, order, title)
                    .odr(icon)
                    .setCheckable(true)
                    .setOnMenuItemClickListener {
                        block(event)
                    }
            }
        }
    }

    private fun MenuItem.odr(name: String) = apply {
        //Implements ODR
        icon = getDrawable(name)
    }

    private fun getDrawable(name: String): Drawable? {
        val id = context.resources.getIdentifier(name, "drawable", context.packageName)
        return if (id > 0) {
            ContextCompat.getDrawable(context, id)
        } else {
            ContextCompat.getDrawable(
                context,
                R.drawable.ic_menu_item_default
            )
        }
    }
}
