package com.pickyberry.rtuitlab_recruit.data.network

import com.google.gson.annotations.SerializedName

data class SimpleCoinPriceDto(
    val prices: Map<String, CoinPrice>
)

data class CoinPrice(
    @SerializedName("usd") val usd: Double?,
    @SerializedName("usd_24h_change")
    val usd24hChange: Double?,
    @SerializedName("rub") val rub: Double?,
    @SerializedName("rub_24h_change")
    val rub24hChange: Double?,
)