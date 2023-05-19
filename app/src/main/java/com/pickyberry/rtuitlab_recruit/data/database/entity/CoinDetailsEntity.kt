package com.pickyberry.rtuitlab_recruit.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_details_entity")
data class CoinDetailsEntity(
    @PrimaryKey val id: String,

    @ColumnInfo(name="symbol") val symbol: String,
    @ColumnInfo(name="name") val name: String,
    @ColumnInfo(name="hashingAlgorithm") val hashingAlgorithm: String,
    @ColumnInfo(name="description") val description: List<String>,
    @ColumnInfo(name="links") val links: Map<String,String>,
    @ColumnInfo(name="image") val image: String,
    @ColumnInfo(name="currentPrice") val currentPrice: List<Double?>,
    @ColumnInfo(name="marketCap") val marketCap: List<Double?>,
    @ColumnInfo(name="fullyDilutedValuation") val fullyDilutedValuation: List<Double?>?,
    @ColumnInfo(name="totalVolume") val totalVolume: List<Double?>,
    @ColumnInfo(name="totalSupply")  val totalSupply: Double?,
    @ColumnInfo(name="maxSupply") val maxSupply: Double?,
    @ColumnInfo(name="circulatingSupply") val circulatingSupply: Double?
)