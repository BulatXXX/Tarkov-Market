package com.singularity.tarkov_market_data.remote.models

data class DetailedItem(
    val id: String,
    val name: String,
    val description: String,
    val avg24hPriceRegular: Int?,
    val low24hPriceRegular: Int?,
    val avg24hPricePve: Int?,
    val low24hPricePve: Int?,
    val iconLink: String,
    val image512PxLink: String,
    val isFavourite: Boolean = false
)

