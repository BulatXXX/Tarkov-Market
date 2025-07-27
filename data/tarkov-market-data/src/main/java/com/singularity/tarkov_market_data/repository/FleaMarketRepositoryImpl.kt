package com.singularity.tarkov_market_data.repository

import com.singularity.tarkov_market_data.type.GameMode
import com.singularity.tarkov_market_data.type.LanguageCode
import com.singularity.tarkov_market_data.models.SearchedItem
import com.singularity.tarkov_market_data.local.dao.ItemFavouriteDao
import com.singularity.tarkov_market_data.local.dao.ItemInfoDao
import com.singularity.tarkov_market_data.local.dao.ItemPriceDao
import com.singularity.tarkov_market_data.models.DetailedItem
import com.singularity.tarkov_market_data.remote.services.TarkovMarketService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

const val PRICE_IS_NULL = -1

internal class FleaMarketRepositoryImpl @Inject constructor(
    private val tarkovMarketService: TarkovMarketService,
    private val infoDao: ItemInfoDao,
    private val priceDao: ItemPriceDao,
    private val favDao: ItemFavouriteDao,
) : FleaMarketRepository {
    override fun search(
        query: String,
        language: LanguageCode,
        gameMode: GameMode,
        limit: Int,
        offset: Int,
    ): Flow<List<SearchedItem>> = flow {
        val itemsList = tarkovMarketService.searchByName(
            query = query,
            language = language,
            gameMode = gameMode,
            limit = limit,
            offset = offset,
        ).filterNotNull().map {
            SearchedItem(
                id = it.id,
                name = it.name.orEmpty(),
                avg24hPrice = it.avg24hPrice ?: PRICE_IS_NULL,
                low24hPrice = it.low24hPrice ?: PRICE_IS_NULL,
                iconLink = it.iconLink.orEmpty(),
            )
        }
        emit(itemsList)
    }.flowOn(Dispatchers.IO)

    override fun getItemById(id: String, language: LanguageCode): Flow<DetailedItem> = flow {
        val detailedItem = tarkovMarketService
            .getItemById(id = id, language = language)
        emit(detailedItem)
    }.flowOn(Dispatchers.IO)

}