package com.example.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringListTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(strings: List<String>?): String? {
        return gson.toJson(strings)
    }

    @TypeConverter
    fun toStringList(stringString: String?): List<String>? {
        if (stringString == null) {
            return null
        }
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(stringString, type)
    }
}