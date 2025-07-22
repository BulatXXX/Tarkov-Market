package com.singularity.tarkov_market_data.remote.models

data class SearchedItem(
    val id: String,
    val name: String,
    val avg24hPrice: Int?,
    val low24hPrice: Int?,
    val iconLink: String,
    val isFavourite: Boolean = false
)