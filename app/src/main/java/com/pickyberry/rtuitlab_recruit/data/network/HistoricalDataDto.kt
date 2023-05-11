package com.pickyberry.rtuitlab_recruit.data.network

import com.google.gson.annotations.SerializedName

data class HistoricalDataDto(
    @SerializedName("prices") val prices: List<List<Float>> = arrayListOf()
)