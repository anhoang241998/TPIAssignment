package com.annguyenhoang.tpiassignment.di

import com.annguyenhoang.tpiassignment.utils.Constant.IO_DISPATCHER
import com.annguyenhoang.tpiassignment.views.tourist_list.TouristListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        TouristListViewModel(
            repository = get(),
            networkChecker = get(),
            ioDispatcher = get(named(IO_DISPATCHER)),
            mainDispatcher = get()
        )
    }
}