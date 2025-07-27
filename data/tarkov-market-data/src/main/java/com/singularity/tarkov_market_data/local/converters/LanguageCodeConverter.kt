package com.singularity.tarkov_market_data.local.converters

import androidx.room.TypeConverter
import com.singularity.tarkov_market_data.type.LanguageCode

class LanguageCodeConverter {
    @TypeConverter
    fun toEnum(value: String): LanguageCode =
        LanguageCode.valueOf(value)

    @TypeConverter
    fun fromEnum(code: LanguageCode): String =
        code.name
}

