package com.annguyenhoang.tpiassignment.utils

import androidx.annotation.StringRes

sealed class UiText {
    data class StringResource(
        @StringRes
        val id: Int,
        val args: List<Any> = emptyList()
    ) : UiText()
}