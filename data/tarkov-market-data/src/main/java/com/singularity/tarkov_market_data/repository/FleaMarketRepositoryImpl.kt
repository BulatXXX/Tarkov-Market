package com.singularity.tarkov_market_data.repository

import com.singularity.tarkov_market_data.type.GameMode
import com.singularity.tarkov_market_data.type.LanguageCode
import com.singularity.tarkov_market_data.models.SearchedItem
import com.singularity.tarkov_market_data.local.dao.ItemFavouriteDao
import com.singularity.tarkov_market_data.local.dao.ItemInfoDao
import com.singularity.tarkov_market_data.local.dao.ItemPriceDao
import com.singularity.tarkov_market_data.local.db.TarkovItemsDatabase
import com.singularity.tarkov_market_data.local.entities.ItemFavourite
import com.singularity.tarkov_market_data.local.entities.toItemInfo
import com.singularity.tarkov_market_data.local.entities.toItemPrice
import com.singularity.tarkov_market_data.models.DetailedItem
import com.singularity.tarkov_market_data.models.FavouriteItem
import com.singularity.tarkov_market_data.remote.services.TarkovMarketService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


internal class FleaMarketRepositoryImpl @Inject constructor(
    private val db: TarkovItemsDatabase,
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
                avg24hPrice = it.avg24hPrice,
                low24hPrice = it.low24hPrice,
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
            favDao.insert(ItemFavourite(id))
            priceDao.upsert(detailedItem.toItemPrice(GameMode.pve))
            priceDao.upsert(detailedItem.toItemPrice(GameMode.regular))
            infoDao.upsert(detailedItem.toItemInfo(language))
        }
    }

    override suspend fun deleteItem(id: String) {

        favDao.delete(id)
        infoDao.delete(id)
        priceDao.delete(id)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeFavourites(
        languageCode: LanguageCode,
        gameMode: GameMode
    ): Flow<List<FavouriteItem>> =
        favDao.observeFavouritesIds().flowOn(Dispatchers.IO).flatMapLatest { ids ->
            if (ids.isEmpty()) return@flatMapLatest flowOf(emptyList())
            val itemFlows: List<Flow<FavouriteItem?>> = ids.map { id ->
                combine(
                    infoDao.observeInfo(id, languageCode),
                    priceDao.observePrice(id, gameMode),
                ) { info, price ->
                    if (info != null && price != null) {
                        FavouriteItem(
                            id = id,
                            name = info.name,
                            iconLink = info.iconLink,
                            avg24hPrice = price.avg24Price,
                            low24hPrice = price.low24Price
                        )
                    } else null
                }

            }
            combine(itemFlows) { list -> list.filterNotNull() }
        }.distinctUntilChanged()
}