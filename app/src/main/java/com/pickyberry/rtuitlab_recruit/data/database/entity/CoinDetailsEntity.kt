package com.pickyberry.rtuitlab_recruit.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_details_entity")
data class CoinDetailsEntity(
    @PrimaryKey val id: String,
    val symbol: String,
    val name: String,
    val hashingAlgorithm: String,
    val description: List<String>,
    val links: Map<String,String>,
    val image: String,
    val currentPrice: List<Double?>,
    val marketCap: List<Double?>,
    val fullyDilutedValuation: List<Double?>,
    val totalVolume: List<Double?>,
    val totalSupply: Double?,
    val maxSupply: Double?,
    val circulatingSupply: Double?
)