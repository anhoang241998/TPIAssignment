package com.annguyenhoang.tpiassignment.di

import com.annguyenhoang.tpiassignment.core.network.ApiResponseHandler
import com.annguyenhoang.tpiassignment.core.network.NetworkChecker
import com.annguyenhoang.tpiassignment.core.network.NetworkCheckerImpl
import com.annguyenhoang.tpiassignment.core.network.NetworkLoggingInterceptor
import com.annguyenhoang.tpiassignment.core.network.RetrofitFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val networkModules = module {
    single {
        OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
    }

    single {
        val client = get<OkHttpClient.Builder>()
            .addInterceptor(NetworkLoggingInterceptor())
            .build()

        RetrofitFactory().createDefaultRetrofit(client = client)
    }

    single<NetworkChecker> {
        NetworkCheckerImpl(androidApplication().applicationContext)
    }

    factoryOf(::ApiResponseHandler)
}