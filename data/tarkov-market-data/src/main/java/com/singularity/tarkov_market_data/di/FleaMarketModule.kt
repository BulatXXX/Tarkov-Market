package com.singularity.tarkov_market_data.di

import dagger.Module

@Module(includes = [FleaMarketNetworkModule::class, BindFleaMarketRepositoryModule::class, FleaMarketDatabaseModule::class])
class FleaMarketModule

