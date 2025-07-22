package com.singularity.tarkov_market_data.local.entities

import androidx.room.Entity

@Entity(
    tableName = "item_info",
    primaryKeys = ["itemId", "languageCode"],
)
data class ItemInfo(
    val itemId: String,
    val languageCode: String,
    val name: String,
    val description: String,
    val iconLink: String,
    val image512PxLink: String,
    )
