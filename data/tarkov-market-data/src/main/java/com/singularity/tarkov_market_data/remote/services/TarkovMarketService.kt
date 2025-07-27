package com.singularity.tarkov_market_data.remote.services

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.singularity.tarkov_market_data.GetItemByIdQuery
import com.singularity.tarkov_market_data.SearchItemByNameQuery
import com.singularity.tarkov_market_data.models.DetailedItem
import com.singularity.tarkov_market_data.type.GameMode
import com.singularity.tarkov_market_data.type.LanguageCode

import javax.inject.Inject


class TarkovMarketService @Inject constructor(private val apolloClient: ApolloClient) {
    suspend fun searchByName(
        query: String,
        language: LanguageCode = LanguageCode.en,
        gameMode: GameMode = GameMode.regular,
        limit: Int?,
        offset: Int?,
    ): List<SearchItemByNameQuery.Item?> =
        apolloClient.query(
            SearchItemByNameQuery(
                name = query,
                lang = language,
                gamemode = gameMode,
                limit = Optional.presentIfNotNull(limit),
                offset = Optional.presentIfNotNull(offset),
            )
        ).execute().data?.items.orEmpty()

    suspend fun getItemById(
        id: String,
        language: LanguageCode = LanguageCode.en
    ): DetailedItem {
        val resp = apolloClient.query(
            GetItemByIdQuery(
                id = id,
                lang = language,
            )
        ).execute().data?: throw RuntimeException("Null data")
        val info = resp.info ?: throw RuntimeException("Null data")
        val pve = resp.pve_price ?: throw RuntimeException("Null data")
        val pvp = resp.pvp_price ?: throw RuntimeException("Null data")
        return DetailedItem(
            id = info.id,
            name = info.name.orEmpty(),
            description = info.description.orEmpty(),
            avg24hPricePvp = pvp.avg24hPrice,
            low24hPricePvp = pvp.low24hPrice,
            avg24hPricePve = pve.avg24hPrice,
            low24hPricePve = pve.low24hPrice,
            iconLink = info.iconLink.orEmpty(),
            image512PxLink = info.image512pxLink.orEmpty(),
        )
    }

}