package com.singularity.tarkov_market_data.di

import android.content.Context
import androidx.room.Room
import com.singularity.tarkov_market_data.local.db.TarkovItemsDatabase
import dagger.Module
import dagger.Provides

@Module
internal object FleaMarketDatabaseModule {

    @Provides
    fun provideDatabase(
        context: Context
    ): TarkovItemsDatabase =
        Room.databaseBuilder(
            context,
            TarkovItemsDatabase::class.java,
            "tarkov.db"
        ).build()

    @Provides
    fun provideFavouriteDao(db: TarkovItemsDatabase) = db.favouriteDao()

    @Provides
    fun provideInfoDao(db: TarkovItemsDatabase) = db.infoDao()

    @Provides
    fun providePriceDao(db: TarkovItemsDatabase) = db.priceDao()
}