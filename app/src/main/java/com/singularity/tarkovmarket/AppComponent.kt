package com.singularity.tarkovmarket

import android.content.Context
import com.singularity.tarkov_market_data.di.FleaMarketModule
import com.singularity.tarkov_market.di.FleaMarketDeps
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [FleaMarketModule::class]
)
interface AppComponent: FleaMarketDeps{
    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appContext: Context
        ): AppComponent
    }
}

