package com.singularity.tarkov_remote.di

import dagger.Module

@Module(includes = [FleaMarketNetworkModule::class, BindFleaMarketRepositoryModule::class])
class FleaMarketModule

