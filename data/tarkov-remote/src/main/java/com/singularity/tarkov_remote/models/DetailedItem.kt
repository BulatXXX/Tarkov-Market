package com.singularity.tarkov_remote.models

data class DetailedItem(
    val id: String,
    val name: String,
    val description: String,
    val avg24hPriceRegular: Int?,
    val low24hPriceRegular: Int?,
    val avg24hPricePve: Int?,
    val low24hPricePve: Int?,
    val iconLink: String,
    val image512pxLink: String,
    val isFavourite: Boolean = false
)

data class SearchedItem(
    val id: String,
    val name: String,
    val avg24hPrice: Int?,
    val low24hPrice: Int?,
    val iconLink: String,
    val isFavourite: Boolean = false
)
