package com.annguyenhoang.tpiassignment.di

import com.annguyenhoang.tpiassignment.models.remote.AttractionsApi
import com.annguyenhoang.tpiassignment.repositories.AttractionsRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import retrofit2.Retrofit

val repositoryModule = module {
    factory { get<Retrofit>().create(AttractionsApi::class.java) }
    factoryOf(::AttractionsRepository)
}