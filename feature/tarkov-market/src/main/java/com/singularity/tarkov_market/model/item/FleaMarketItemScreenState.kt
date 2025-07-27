package com.singularity.tarkov_market.model.item

import com.singularity.tarkov_market_data.models.DetailedItem

internal data class FleaMarketItemScreenState(
    val isLoading: Boolean = false,
    val itemId: String? = null,
    val detailedItem: DetailedItem? = null,
    val error: String? = null
)