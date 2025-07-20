package com.singularity.tarkov_remote.models.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.singularity.tarkov_remote.models.entities.ItemInfo

@Dao
interface ItemInfoDao {
    @Query("SELECT * FROM item_info WHERE itemId = :id AND mode = :mode")
    fun get(id: String, mode: String): ItemInfo

    @Upsert
    fun upsert(item: ItemInfo)
}