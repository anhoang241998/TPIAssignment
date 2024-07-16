package com.annguyenhoang.tpiassignment.views.tourist_list.models

import android.os.Parcelable
import com.annguyenhoang.tpiassignment.views.tourist_list.adapters.TouristsAdapter.Companion.TOURIST_ITEM_VIEW_TYPE
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tourist(
    override val id: String,
    val touristTitle: String,
    val touristDescription: String,
    val imageUrl: String,
    val touristUrl: String
) : Parcelable, TouristRecyclerViewType() {
    override val viewType: Int
        get() = TOURIST_ITEM_VIEW_TYPE
}