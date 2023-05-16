package com.pickyberry.rtuitlab_recruit.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_item_entity")
data class CoinItemEntity(
    @PrimaryKey val id: String,

    @ColumnInfo(name = "name", defaultValue = "") val name: String,
    @ColumnInfo(name = "symbol", defaultValue = "") val symbol: String,
    @ColumnInfo(name = "image", defaultValue = "") val image: String,
    @ColumnInfo(name = "currentPrice", defaultValue = "0") val currentPrice: Float,
    @ColumnInfo(name = "priceChangePercentage", defaultValue = "0") val priceChangePercentage: Float,
    @ColumnInfo(name = "marketCapChangePercentage", defaultValue = "0") val marketCapChangePercentage: Float,
    @ColumnInfo(name = "isFavorite") val isFavorite:Boolean = false
)

