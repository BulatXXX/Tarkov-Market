package com.singularity.tarkov_market_data.local.entities

import androidx.room.Entity

@Entity(
    tableName = "item_price",
    primaryKeys = ["itemId", "mode"],
)
data class ItemPrice(
    val itemId: String,
    val mode: String,
    val avg24Price: Int,
    val low24Price: Int,
    val updatedAt: Long,
)