package com.singularity.tarkov_market_data.local.converters

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class DateTimeConverter {
    private val zone = ZoneOffset.UTC

    @TypeConverter
    fun toDateTime(millis: Long): LocalDateTime =
        Instant.ofEpochMilli(millis).atZone(zone).toLocalDateTime()

    @TypeConverter
    fun fromDateTime(dt: LocalDateTime): Long =
        dt.atZone(zone).toInstant().toEpochMilli()
}