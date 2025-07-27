package com.singularity.tarkov_market_data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.singularity.tarkov_market_data.local.entities.ItemPrice

@Dao
interface ItemPriceDao {
    @Upsert
    suspend fun upsert(itemInfo: ItemPrice)

    @Query("DELETE FROM item_price WHERE itemId= :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM item_price WHERE itemId= :id")
    suspend fun get(id: String): ItemPrice
}