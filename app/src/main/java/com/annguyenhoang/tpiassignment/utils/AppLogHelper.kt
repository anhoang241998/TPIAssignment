package com.annguyenhoang.tpiassignment.utils

import timber.log.Timber

object AppLogHelper {

    fun logDebug(msg: String? = null) {
        val logMsg = when {
            msg.isNullOrEmpty().not() -> msg
            else -> "Unknown"
        }

        Timber.d(logMsg)
    }

    fun logError(t: Throwable? = null) {
        val logMsg = when {
            t != null && t.message.isNullOrEmpty().not() -> "${t.message}"
            else -> "Unknown Error"
        }

        Timber.e(logMsg)
    }

}