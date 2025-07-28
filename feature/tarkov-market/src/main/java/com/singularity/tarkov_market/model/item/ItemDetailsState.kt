package com.singularity.tarkov_market.model.item

import com.singularity.tarkov_market_data.models.DetailedItem

internal data class ItemDetailsState(
    val isLoading: Boolean = false,
    val detailedItem: DetailedItem? = null,
    val error: String? = null
)