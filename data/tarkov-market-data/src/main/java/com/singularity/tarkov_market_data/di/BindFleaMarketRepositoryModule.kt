package com.singularity.tarkov_market_data.di

import com.singularity.tarkov_market_data.repository.FleaMarketRepository
import com.singularity.tarkov_market_data.repository.FleaMarketRepositoryImpl
import dagger.Binds
import dagger.Module

@Module()
internal interface BindFleaMarketRepositoryModule{
    @Binds
    fun bindRepositoryImpl(impl: FleaMarketRepositoryImpl): FleaMarketRepository
}