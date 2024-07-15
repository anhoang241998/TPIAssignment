package com.annguyenhoang.tpiassignment.di

import com.annguyenhoang.tpiassignment.utils.Constant.DEFAULT_DISPATCHER
import com.annguyenhoang.tpiassignment.utils.Constant.IO_DISPATCHER
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coroutineModule = module {
    factory<CoroutineDispatcher>(named(IO_DISPATCHER)) {
        Dispatchers.IO
    }
    factory<CoroutineDispatcher>(named(DEFAULT_DISPATCHER)) {
        Dispatchers.Default
    }
    factory<MainCoroutineDispatcher> {
        Dispatchers.Main
    }
}