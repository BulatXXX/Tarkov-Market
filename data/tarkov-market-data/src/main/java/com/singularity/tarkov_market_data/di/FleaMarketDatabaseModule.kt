package com.singularity.tarkov_market_data.di

import android.content.Context
import androidx.room.Room
import com.singularity.tarkov_market_data.local.db.TarkovItemsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal object FleaMarketDatabaseModule {

    @Provides @Singleton
    fun provideDatabase(
        context: Context
    ): TarkovItemsDatabase =
        Room.databaseBuilder(
            context,
            TarkovItemsDatabase::class.java,
            "tarkov.db"
        ).enableMultiInstanceInvalidation().build()

    @Provides
    fun provideFavouriteDao(db: TarkovItemsDatabase) = db.favouriteDao()

    @Provides
    fun provideInfoDao(db: TarkovItemsDatabase) = db.infoDao()

    @Provides
    fun providePriceDao(db: TarkovItemsDatabase) = db.priceDao()
}