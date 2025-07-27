package com.singularity.tarkov_market_data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.singularity.tarkov_market_data.local.entities.ItemFavourite

@Dao
interface ItemFavouriteDao {

    @Upsert
    suspend fun upsert(itemInfo: ItemFavourite)

    @Query("DELETE FROM item_favourite WHERE itemId= :id")
    suspend fun delete(id: String)

    @Query("SELECT EXISTS(SELECT * FROM item_favourite WHERE itemId= :id)")
    suspend fun isFavourite(id: String): Boolean

}