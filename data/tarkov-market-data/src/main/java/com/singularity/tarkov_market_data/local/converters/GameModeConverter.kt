package com.singularity.tarkov_market_data.local.converters

import androidx.room.TypeConverter
import com.singularity.tarkov_market_data.type.GameMode

class GameModeConverter{
    @TypeConverter
    fun toEnum(value: String): GameMode =
        GameMode.valueOf(value)

    @TypeConverter
    fun fromEnum(code: GameMode): String =
        code.name
}