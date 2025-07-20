package com.singularity.tarkov_remote.di

import com.singularity.tarkov_remote.repository.FleaMarketRepository
import com.singularity.tarkov_remote.repository.FleaMarketRepositoryImpl
import dagger.Binds
import dagger.Module

@Module()
internal interface BindFleaMarketRepositoryModule{
    @Binds
    fun bindRepositoryImpl(impl: FleaMarketRepositoryImpl): FleaMarketRepository
}