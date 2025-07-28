package com.singularity.tarkov_market_data.local.entities

import androidx.room.Entity
import com.singularity.tarkov_market_data.models.DetailedItem
import com.singularity.tarkov_market_data.type.GameMode
import java.time.LocalDateTime

@Entity(
    tableName = "item_price",
    primaryKeys = ["itemId", "mode"],
)
data class ItemPrice(
    val itemId: String,
    val mode: GameMode,
    val avg24Price: Int?,
    val low24Price: Int?,
    val updatedAt: LocalDateTime,
)

internal fun DetailedItem.toItemPrice(gameMode: GameMode) = ItemPrice(
    itemId = id,
    mode = gameMode,
    avg24Price = if (gameMode == GameMode.pve) avg24hPricePve else avg24hPricePvp,
    low24Price = if (gameMode == GameMode.pve) low24hPricePve else low24hPricePvp,
    updatedAt = LocalDateTime.now()
)