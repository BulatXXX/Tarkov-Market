package com.singularity.tarkov_market_data.local.dao

import androidx.room.Dao

import androidx.room.Query
import androidx.room.Upsert
import com.singularity.tarkov_market_data.local.entities.ItemInfo

@Dao
interface ItemInfoDao {
    @Upsert
    suspend fun upsert(itemInfo: ItemInfo)

    @Query("DELETE FROM item_info WHERE itemId= :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM item_info WHERE itemId= :id")
    suspend fun get(id: String): ItemInfo
}

