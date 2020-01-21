package com.lcmobile.navigation

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Navigation(
    @SerializedName("type") val type: NavigationType,
    @SerializedName("items") val items: List<NavigationItem>,
    @SerializedName("sub_items") val subItems: List<NavigationItem>
) : Parcelable

enum class NavigationType {
    @SerializedName("drawer")
    Drawer,
    @SerializedName("bottom")
    Bottom,
    @SerializedName("both")
    Both
}

@Parcelize
data class NavigationItem(
    @SerializedName("group") val group: Int = 1,
    @SerializedName("item_id") val itemId: Int,
    @SerializedName("order") val order: Int = 1,
    @SerializedName("title") val title: String,
    @SerializedName("icon") val icon: String,
    @SerializedName("event") val event: NavigationEvent
) : Parcelable

enum class NavigationEventType {
    @SerializedName("deeplink")
    Deeplink,
    @SerializedName("fragment")
    Fragment
}

@Parcelize
data class NavigationEvent(
    @SerializedName("type") val type: NavigationEventType,
    @SerializedName("data") val data: String,
    @SerializedName("extra") val extra: Map<String, String>? = null,
    @SerializedName("analytics") val analytics: NavigationAnalytics? = null
) : Parcelable

@Parcelize
data class NavigationAnalytics(
    @SerializedName("type") val type: NavigationEventType,
    @SerializedName("data") val data: String,
    @SerializedName("extra") val extra: Map<String, String>? = null
) : Parcelable