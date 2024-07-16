package com.annguyenhoang.tpiassignment.views.tourist_list.models

@Suppress("CovariantEquals")
open class TouristRecyclerViewType {
    open val id: String = ""
    open val viewType: Int = 0

    fun equals(other: TouristRecyclerViewType): Boolean {
        return this.id == other.id && this.viewType == other.viewType
    }
}