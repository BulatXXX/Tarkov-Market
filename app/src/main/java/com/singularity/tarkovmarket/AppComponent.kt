package com.singularity.tarkovmarket

import com.singularity.tarkov_remote.di.FleaMarketModule
import com.singularity.tarkov_market.di.FleaMarketDeps
import dagger.Component

@Component(
    modules = [FleaMarketModule::class]
)
interface AppComponent: FleaMarketDeps{
    fun inject(activity: MainActivity)
}