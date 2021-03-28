package com.aaronbruckner.criminalintent.database

import androidx.room.TypeConverter
import java.util.*

class CrimeTypeConverts {
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(timestampMS: Long?): Date? {
        return timestampMS?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }

    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return uuid?.let {
            UUID.fromString(uuid)
        }
    }
}