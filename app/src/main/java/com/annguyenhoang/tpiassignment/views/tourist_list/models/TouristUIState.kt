package com.annguyenhoang.tpiassignment.views.tourist_list.models

import com.annguyenhoang.tpiassignment.utils.UiText

data class TouristUIState(
    val isLoading: Boolean = false,
    val tourists: List<Tourist> = emptyList(),
    val error: UiText? = null,
    val errorMsgFromServer: String? = null
)