package com.singularity.tarkov_market_data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.singularity.tarkov_market_data.local.entities.ItemPrice
import com.singularity.tarkov_market_data.type.GameMode
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemPriceDao {
    @Upsert
    suspend fun upsert(itemInfo: ItemPrice)

    @Query("DELETE FROM item_price WHERE itemId= :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM item_price WHERE itemId= :id")
    suspend fun get(id: String): ItemPrice


    @Query(
        """
        SELECT * FROM item_price
        WHERE itemId = :id AND mode = :mode
        LIMIT 1
    """
    )
    fun observePrice(id: String, mode: GameMode): Flow<ItemPrice?>

}