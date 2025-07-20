package com.singularity.tarkovmarket

import android.app.Application
import com.singularity.tarkov_market.di.FleaMarketDepsStore

class TarkovApp : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
        FleaMarketDepsStore.deps = appComponent
    }
}