package com.pickyberry.rtuitlab_recruit.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: String?): List<String>? {
        return value?.split(",")?.map { it.trim() }
    }

    @TypeConverter
    fun toStringList(list: List<String>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    fun fromDoubleList(value: String?): List<Double>? {
        return value?.split(",")?.map { it.trim().toDouble() }
    }

    @TypeConverter
    fun toDoubleList(list: List<Double>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    fun fromStringMap(value: String?): Map<String, String>? {
        if (value == null) {
            return null
        }
        val mapType = object : TypeToken<Map<String, String>>() {}.type
        return gson.fromJson(value, mapType)
    }

    @TypeConverter
    fun toStringMap(map: Map<String, String>?): String? {
        return gson.toJson(map)
    }

    @TypeConverter
    fun fromPairList(value: String?): List<Pair<Float, Float>>? {
        return value?.split(";")?.mapNotNull {
            val pair = it.split(",")
            if (pair.size == 2) {
                Pair(pair[0].toFloat(), pair[1].toFloat())
            } else {
                null
            }
        }
    }

    @TypeConverter
    fun toPairList(list: List<Pair<Float, Float>>?): String? {
        return list?.joinToString(";") { "${it.first},${it.second}" }
    }
}