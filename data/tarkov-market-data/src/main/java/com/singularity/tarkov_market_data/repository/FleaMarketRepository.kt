package com.singularity.tarkov_market_data.repository

import com.singularity.tarkov_market_data.models.DetailedItem
import com.singularity.tarkov_market_data.models.FavouriteItem
import com.singularity.tarkov_market_data.type.GameMode
import com.singularity.tarkov_market_data.type.LanguageCode
import com.singularity.tarkov_market_data.models.SearchedItem
import kotlinx.coroutines.flow.Flow

interface FleaMarketRepository {
    fun search(
        query: String,
        language: LanguageCode = LanguageCode.en,
        gameMode: GameMode = GameMode.regular,
        limit: Int = 1000,
        offset: Int = 0,
    ): Flow<List<SearchedItem>>
    fun getItemById(id: String, language: LanguageCode): Flow<DetailedItem>

    suspend fun saveItem(detailedItem: DetailedItem, language: LanguageCode = LanguageCode.en)
    suspend fun deleteItem(id: String)
    fun observeFavourites(languageCode: LanguageCode,gameMode: GameMode): Flow<List<FavouriteItem>>

}