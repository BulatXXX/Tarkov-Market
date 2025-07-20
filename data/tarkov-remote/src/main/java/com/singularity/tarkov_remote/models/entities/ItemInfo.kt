package com.singularity.tarkov_remote.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "item_language_info",
    primaryKeys = ["itemId", "languageCode"],
)
data class ItemInfo(
    @PrimaryKey val itemId: String,
    @PrimaryKey val languageCode: String,
    val name: String,
    val description: String,
    val iconLink: String,
    val image512PxLink: String,
    )
