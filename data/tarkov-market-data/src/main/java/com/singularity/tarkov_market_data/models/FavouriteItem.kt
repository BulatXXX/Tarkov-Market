package com.singularity.tarkov_market_data.models

data class FavouriteItem(
    val id: String,
    val name: String,
    val avg24hPrice: Int?,
    val low24hPrice: Int?,
    val iconLink: String,
)