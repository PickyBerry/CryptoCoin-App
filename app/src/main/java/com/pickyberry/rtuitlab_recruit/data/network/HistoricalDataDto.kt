package com.pickyberry.rtuitlab_recruit.data.network

import com.google.gson.annotations.SerializedName

data class HistoricalDataDto(
    @SerializedName("prices") val prices: List<List<Float>>,
    @SerializedName("market_caps") val marketCaps: List<List<Float>>,
    @SerializedName("total_volumes") val totalVolumes: List<List<Float>>
)