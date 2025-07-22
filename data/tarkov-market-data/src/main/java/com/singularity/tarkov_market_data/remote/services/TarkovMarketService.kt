package com.singularity.tarkov_market_data.remote.services

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.singularity.tarkov_market_data.SearchItemByNameQuery
import com.singularity.tarkov_market_data.type.GameMode
import com.singularity.tarkov_market_data.type.LanguageCode

import javax.inject.Inject

class TarkovMarketService @Inject constructor(val apolloClient: ApolloClient) {
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


}