package com.pickyberry.rtuitlab_recruit.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historical_data_entity")
data class HistoricalDataEntity(
    @PrimaryKey val id:String,
    @ColumnInfo(name="prices") val prices: List<Pair<Float,Float>> = arrayListOf()
)
