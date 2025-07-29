package com.singularity.tarkov_market_data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.singularity.tarkov_market_data.local.entities.ItemFavourite
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemFavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(itemInfo: ItemFavourite)

    @Query("DELETE FROM item_favourite WHERE itemId= :id")
    suspend fun delete(id: String)

    @Query("SELECT EXISTS(SELECT * FROM item_favourite WHERE itemId= :id)")
    fun isFavourite(id: String): Flow<Boolean>

    @Query("SELECT * FROM item_favourite")
    fun observeFavouritesIds(): Flow<List<String>>

}