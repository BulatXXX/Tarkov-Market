package com.singularity.tarkov_remote.repository

import com.singularity.tarkov_remote.type.GameMode
import com.singularity.tarkov_remote.type.LanguageCode
import com.singularity.tarkov_remote.models.DetailedItem
import com.singularity.tarkov_remote.models.SearchedItem

interface FleaMarketRepository {
    suspend fun search(
        query: String,
        language: LanguageCode = LanguageCode.en,
        gameMode: GameMode = GameMode.regular,
        limit: Int = 1000,
        offset: Int = 0,
    ): List<SearchedItem>

    suspend fun getItemById(itemId: String): DetailedItem
}