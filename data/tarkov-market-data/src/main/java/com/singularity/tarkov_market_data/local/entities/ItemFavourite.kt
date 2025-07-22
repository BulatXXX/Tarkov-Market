package com.singularity.tarkov_market_data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_favourite")
data class ItemFavourite(
    @PrimaryKey val itemId: String,
)
