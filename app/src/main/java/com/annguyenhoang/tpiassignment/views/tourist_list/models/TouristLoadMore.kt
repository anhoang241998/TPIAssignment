package com.annguyenhoang.tpiassignment.views.tourist_list.models

import com.annguyenhoang.tpiassignment.views.tourist_list.adapters.TouristsAdapter.Companion.LOAD_MORE_ITEM_VIEW_TYPE

data class TouristLoadMore(
    override val id: String,
) : TouristRecyclerViewType() {
    override val viewType: Int
        get() = LOAD_MORE_ITEM_VIEW_TYPE
}
