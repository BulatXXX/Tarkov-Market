package com.singularity.tarkov_market_data.local.dao

import androidx.room.Dao

import androidx.room.Query
import androidx.room.Upsert
import com.singularity.tarkov_market_data.local.entities.ItemInfo
import com.singularity.tarkov_market_data.type.LanguageCode
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemInfoDao {
    @Upsert
    suspend fun upsert(itemInfo: ItemInfo)

    @Query("DELETE FROM item_info WHERE itemId= :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM item_info WHERE itemId= :id")
    suspend fun get(id: String): ItemInfo

    @Query("""
        SELECT * FROM item_info
        WHERE itemId = :id AND languageCode = :lang
        LIMIT 1
    """)
    fun observeInfo(id: String, lang: LanguageCode): Flow<ItemInfo?>
}

