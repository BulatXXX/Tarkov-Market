package com.singularity.tarkov_market_data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.singularity.tarkov_market_data.local.converters.DateTimeConverter
import com.singularity.tarkov_market_data.local.converters.GameModeConverter
import com.singularity.tarkov_market_data.local.converters.LanguageCodeConverter
import com.singularity.tarkov_market_data.local.dao.ItemFavouriteDao
import com.singularity.tarkov_market_data.local.dao.ItemInfoDao
import com.singularity.tarkov_market_data.local.dao.ItemPriceDao
import com.singularity.tarkov_market_data.local.entities.ItemFavourite
import com.singularity.tarkov_market_data.local.entities.ItemInfo
import com.singularity.tarkov_market_data.local.entities.ItemPrice

@Database(
    entities = [ItemFavourite::class, ItemInfo::class, ItemPrice::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(LanguageCodeConverter::class, GameModeConverter::class, DateTimeConverter::class)
abstract class TarkovItemsDatabase : RoomDatabase() {
    abstract fun favouriteDao(): ItemFavouriteDao
    abstract fun infoDao(): ItemInfoDao
    abstract fun priceDao(): ItemPriceDao
}