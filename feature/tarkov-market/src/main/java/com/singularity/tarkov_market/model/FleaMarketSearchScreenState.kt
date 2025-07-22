package com.singularity.tarkov_market.model

import com.singularity.tarkov_market_data.remote.models.SearchedItem

internal data class FleaMarketSearchScreenState(
    val query: String = "",
    val searchedItems: List<SearchedItem> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null,
)
