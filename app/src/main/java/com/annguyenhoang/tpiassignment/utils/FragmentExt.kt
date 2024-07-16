package com.annguyenhoang.tpiassignment.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Fragment.observeFlow(flow: Flow<T>, collect: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        flow.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect(collect)
    }
}