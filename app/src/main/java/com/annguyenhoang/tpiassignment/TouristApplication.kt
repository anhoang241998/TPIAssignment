package com.annguyenhoang.tpiassignment

import android.app.Application
import org.koin.core.context.startKoin

class TouristApplication : Application() {

    private val modules = listOf("")

    override fun onCreate() {
        super.onCreate()
//        initKoin()
    }

    private fun initKoin() {
        startKoin {
            modules()
        }
    }

}