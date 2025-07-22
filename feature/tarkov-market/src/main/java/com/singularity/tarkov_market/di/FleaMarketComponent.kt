package com.singularity.tarkov_market.di

import com.singularity.tarkov_market_data.repository.FleaMarketRepository
import dagger.Component
import kotlin.properties.Delegates.notNull

@Component(dependencies = [FleaMarketDeps::class])
internal interface FleaMarketComponent {
    @Component.Builder
    interface Builder {
        fun deps(deps: FleaMarketDeps): Builder
        fun build(): FleaMarketComponent
    }

    val viewModelFactory: FleaMarketViewModelFactory
}

interface FleaMarketDeps {
    val fleaMarketRepository: FleaMarketRepository
}

interface FleaMarketDepsProvider {
    val deps: FleaMarketDeps

    companion object : FleaMarketDepsProvider by FleaMarketDepsStore
}

object FleaMarketDepsStore : FleaMarketDepsProvider {
    override var deps: FleaMarketDeps by notNull()
}