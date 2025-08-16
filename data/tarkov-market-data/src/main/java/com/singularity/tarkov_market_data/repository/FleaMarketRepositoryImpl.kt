package com.singularity.tarkov_market_data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.singularity.tarkov_market_data.local.dao.ItemFavouriteDao
import com.singularity.tarkov_market_data.local.dao.ItemInfoDao
import com.singularity.tarkov_market_data.local.dao.ItemPriceDao
import com.singularity.tarkov_market_data.local.entities.ItemFavourite
import com.singularity.tarkov_market_data.local.entities.toItemInfo
import com.singularity.tarkov_market_data.local.entities.toItemPrice
import com.singularity.tarkov_market_data.models.DetailedItem
import com.singularity.tarkov_market_data.models.FavouriteItem
import com.singularity.tarkov_market_data.models.SearchedItem
import com.singularity.tarkov_market_data.remote.SearchPagingSource
import com.singularity.tarkov_market_data.remote.services.TarkovMarketService
import com.singularity.tarkov_market_data.type.GameMode
import com.singularity.tarkov_market_data.type.LanguageCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject


internal class FleaMarketRepositoryImpl @Inject constructor(
    private val tarkovMarketService: TarkovMarketService,
    private val infoDao: ItemInfoDao,
    private val priceDao: ItemPriceDao,
    private val favDao: ItemFavouriteDao,
) : FleaMarketRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun search(
        query: String,
        language: LanguageCode,
        gameMode: GameMode,
        limit: Int,
        offset: Int
    ): Flow<List<SearchedItem>> = favDao.observeFavouritesIds()
        .map { it.toSet() }
        .flowOn(Dispatchers.IO)
        .flatMapLatest { favIds ->
            flow {
                val items = tarkovMarketService
                    .searchByName(query, language, gameMode, limit, offset)
                    .filterNotNull()
                    .map { dto ->
                        SearchedItem(
                            id = dto.id,
                            name = dto.name.orEmpty(),
                            avg24hPrice = dto.avg24hPrice,
                            low24hPrice = dto.low24hPrice,
                            iconLink = dto.iconLink.orEmpty(),
                            isFavourite = dto.id in favIds
                        )
                    }
                emit(items)
            }
        }
        .flowOn(Dispatchers.IO)

    override fun searchPaged(
        query: String,
        language: LanguageCode,
        gameMode: GameMode
    ): Flow<PagingData<SearchedItem>> =
        Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = {
                SearchPagingSource(
                    tarkovMarketService,
                    query,
                    language,
                    gameMode,
                    10
                )
            }
        ).flow.combine(favDao.observeFavouritesIds().map { it.toSet() }) { paging, favIds ->
            paging.map { it.copy(isFavourite = it.id in favIds) }
        }.flowOn(Dispatchers.IO)


    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getItemById(
        id: String,
        language: LanguageCode
    ): Flow<DetailedItem> = flow {
        emit(tarkovMarketService.getItemById(id, language))
    }.flatMapLatest { item ->
        favDao.isFavourite(id).map { isFavourite ->
            item.copy(isFavourite = isFavourite)
        }
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
        gameMode: GameMode,
    ): Flow<List<FavouriteItem>> =
        favDao.observeFavouritesIds().flowOn(Dispatchers.IO).flatMapLatest { ids ->
            if (ids.isEmpty()) return@flatMapLatest flowOf(emptyList())
            val itemFlows: List<Flow<FavouriteItem?>> = ids.map { id ->
                combine(
                    infoDao.observeInfo(id, languageCode),
                    priceDao.observePrice(id, gameMode),
                ) { info, price ->
                    if (info == null || price == null) return@combine null
                    FavouriteItem(
                        id = id,
                        name = info.name,
                        iconLink = info.iconLink,
                        avg24hPrice = price.avg24Price,
                        low24hPrice = price.low24Price,
                    )
                }
            }
            combine(itemFlows) { list -> list.filterNotNull() }
        }.distinctUntilChanged()
}