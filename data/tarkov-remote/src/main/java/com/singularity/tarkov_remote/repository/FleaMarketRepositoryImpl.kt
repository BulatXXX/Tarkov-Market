package com.singularity.tarkov_remote.repository

import com.apollographql.apollo.ApolloClient
import com.singularity.tarkov_remote.SearchItemByNameQuery
import com.singularity.tarkov_remote.type.GameMode
import com.singularity.tarkov_remote.type.LanguageCode
import com.singularity.tarkov_remote.models.DetailedItem
import com.singularity.tarkov_remote.models.SearchedItem
import javax.inject.Inject

internal class FleaMarketRepositoryImpl @Inject constructor(val apolloClient: ApolloClient) :
    FleaMarketRepository {
    override suspend fun search(
        query: String,
        language: LanguageCode,
        gameMode: GameMode,
        limit: Int,
        offset: Int,
    ): List<SearchedItem> {
        val res = apolloClient.query(SearchItemByNameQuery(query, language, gameMode))
            .execute().data?.items.orEmpty().filterNotNull().map {
                SearchedItem(
                    id = it.id,
                    name = it.name.toString(),
                    avg24hPrice = it.avg24hPrice ?: -1,
                    low24hPrice = it.low24hPrice ?: -1,
                    iconLink = it.iconLink.toString(),
                )
            }
        return res
    }

    override suspend fun getItemById(itemId: String): DetailedItem {
        TODO("Not yet implemented")
    }
}