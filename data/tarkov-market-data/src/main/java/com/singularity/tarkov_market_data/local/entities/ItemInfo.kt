package com.singularity.tarkov_market_data.local.entities

import androidx.room.Entity
import com.singularity.tarkov_market_data.models.DetailedItem
import com.singularity.tarkov_market_data.type.LanguageCode

@Entity(
    tableName = "item_info",
    primaryKeys = ["itemId", "languageCode"],
)
data class ItemInfo(
    val itemId: String,
    val languageCode: LanguageCode,
    val name: String,
    val description: String,
    val iconLink: String,
    val image512PxLink: String,
    )

internal fun DetailedItem.toItemInfo(languageCode: LanguageCode) = ItemInfo(
    itemId = id,
    name = name,
    description = description,
    iconLink = iconLink,
    image512PxLink = image512PxLink,
    languageCode = languageCode
)
