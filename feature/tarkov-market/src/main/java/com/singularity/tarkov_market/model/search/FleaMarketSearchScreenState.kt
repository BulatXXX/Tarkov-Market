package com.singularity.tarkov_market.model.search

import com.singularity.tarkov_market_data.models.SearchedItem

internal data class FleaMarketSearchScreenState(
    val query: String = "",
    val searchedItems: List<SearchedItem> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null,
)
