package com.singularity.tarkov_market_data.repository

import com.singularity.tarkov_market_data.type.GameMode
import com.singularity.tarkov_market_data.type.LanguageCode
import com.singularity.tarkov_market_data.models.SearchedItem
import com.singularity.tarkov_market_data.local.dao.ItemFavouriteDao
import com.singularity.tarkov_market_data.local.dao.ItemInfoDao
import com.singularity.tarkov_market_data.local.dao.ItemPriceDao
import com.singularity.tarkov_market_data.local.entities.ItemFavourite
import com.singularity.tarkov_market_data.local.entities.ItemInfo
import com.singularity.tarkov_market_data.local.entities.ItemPrice
import com.singularity.tarkov_market_data.models.DetailedItem
import com.singularity.tarkov_market_data.remote.services.TarkovMarketService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
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
        val fav = favDao.isFavourite(id)
        emit(detailedItem.copy(isFavourite = fav))
    }.flowOn(Dispatchers.IO)

    override suspend fun saveItem(detailedItem: DetailedItem, language: LanguageCode) {
        detailedItem.apply {
            favDao.upsert(ItemFavourite(id))
            priceDao.upsert(ItemPrice(
                itemId = id,
                mode = GameMode.pve,
                avg24Price = avg24hPricePve?: PRICE_IS_NULL,
                low24Price = low24hPricePve?: PRICE_IS_NULL,
                updatedAt = LocalDateTime.now()
            ))
            priceDao.upsert(ItemPrice(
                itemId = id,
                mode = GameMode.regular,
                avg24Price = avg24hPricePvp?: PRICE_IS_NULL,
                low24Price = low24hPricePvp?: PRICE_IS_NULL,
                updatedAt = LocalDateTime.now()
            ))
            infoDao.upsert(
                ItemInfo(
                    itemId = id,
                    name = name,
                    description = description,
                    iconLink = iconLink,
                    image512PxLink = image512PxLink,
                    languageCode = language
                )
            )
        }

    }

    override suspend fun deleteItem(id: String) {
        favDao.delete(id)
        infoDao.delete(id)
        priceDao.delete(id)
    }

}