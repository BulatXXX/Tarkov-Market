package com.singularity.tarkov_market.model.favourites

import com.singularity.tarkov_market_data.models.FavouriteItem

internal data class FavouritesState(
    val error: String? = null,
    val favouriteItems: List<FavouriteItem> = emptyList(),
    val isLoading: Boolean = false,
)