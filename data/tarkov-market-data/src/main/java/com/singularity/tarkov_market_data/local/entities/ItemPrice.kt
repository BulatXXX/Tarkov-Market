package com.singularity.tarkov_market_data.local.entities

import androidx.room.Entity
import com.singularity.tarkov_market_data.type.GameMode
import java.time.LocalDateTime

@Entity(
    tableName = "item_price",
    primaryKeys = ["itemId", "mode"],
)
data class ItemPrice(
    val itemId: String,
    val mode: GameMode,
    val avg24Price: Int,
    val low24Price: Int,
    val updatedAt: LocalDateTime,
)