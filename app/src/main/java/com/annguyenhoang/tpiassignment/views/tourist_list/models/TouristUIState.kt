package com.annguyenhoang.tpiassignment.views.tourist_list.models

import com.annguyenhoang.tpiassignment.utils.UiText

data class TouristUIState(
    val isLoading: Boolean = false,
    val tourists: List<Tourist> = emptyList(),
    val error: UiText? = null,
    val errorMsgFromServer: String? = null,
    val currentPage: Int = 1,
    val currentTotalItems: Int = 0,
    val totalTourist: Int = 0,
    val isLoadMore: Boolean = false
)