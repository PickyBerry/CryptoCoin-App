package com.pickyberry.rtuitlab_recruit.data.network.details

import com.google.gson.annotations.SerializedName

data class CoinDetailsMarketCapDto(
    @SerializedName("rub") var rub: Double? = null,
    @SerializedName("usd") var usd: Double? = null,
)