package com.pickyberry.rtuitlab_recruit.data.network

import com.google.gson.annotations.SerializedName
import com.pickyberry.rtuitlab_recruit.domain.model.CoinItem

data class CoinDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("image") val image:String,
    @SerializedName("current_price") val currentPrice: Float,
    @SerializedName("price_change_percentage_24h") val priceChangePercentage: Float,
    @SerializedName("market_cap_change_percentage_24h") val marketCapChangePercentage: Float
)




