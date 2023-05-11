package com.pickyberry.rtuitlab_recruit.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.pickyberry.rtuitlab_recruit.domain.model.CoinItem

@Entity
data class CoinItemEntity(
    @PrimaryKey val id:String,
    val name: String,
    val symbol: String,
    @ColumnInfo(name="image", defaultValue = "")
    val image:String,
    @ColumnInfo(name="currentPrice", defaultValue = "0")
    val currentPrice: Float,
    @ColumnInfo(name="priceChangePercentage", defaultValue = "0")
    val priceChangePercentage: Float,
    @ColumnInfo(name="marketCapChangePercentage", defaultValue ="0")
    val marketCapChangePercentage: Float
)

