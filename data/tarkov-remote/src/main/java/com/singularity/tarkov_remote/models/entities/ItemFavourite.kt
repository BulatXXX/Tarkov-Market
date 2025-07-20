package com.singularity.tarkov_remote.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemFavourite(
    @PrimaryKey val itemId: String,
)
