package com.singularity.tarkov_market_data.models

import com.singularity.tarkov_market_data.SearchItemByNameQuery

data class SearchedItem(
    val id: String,
    val name: String,
    val avg24hPrice: Int?,
    val low24hPrice: Int?,
    val iconLink: String,
    val isFavourite: Boolean = false
)

fun SearchItemByNameQuery.Item.toSearchedItem() = SearchedItem(
    id = id,
    name = name.orEmpty(),
    avg24hPrice = avg24hPrice,
    low24hPrice = low24hPrice,
    iconLink = iconLink.orEmpty(),
)