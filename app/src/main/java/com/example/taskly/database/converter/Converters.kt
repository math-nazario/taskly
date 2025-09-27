package com.example.taskly.database.converter

import androidx.room.TypeConverter
import com.example.taskly.util.Priority

class Converters {
    @TypeConverter
    fun fromPriority(priority: Priority): Int {
        return priority.ordinal
    }

    @TypeConverter
    fun toPriority(value: Int): Priority {
        return Priority.entries[value]
    }
}
