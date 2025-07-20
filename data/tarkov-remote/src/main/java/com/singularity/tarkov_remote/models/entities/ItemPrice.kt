package com.singularity.tarkov_remote.models.entities

import androidx.room.Entity

@Entity(
    tableName = "item_price",
    primaryKeys = ["itemId", "mode"],
)
data class ItemPrice(
    val itemId: String,
    val mode: String,
    val avg24Price: String,
    val low224Price: String,
    val updatedAt: Long,
)