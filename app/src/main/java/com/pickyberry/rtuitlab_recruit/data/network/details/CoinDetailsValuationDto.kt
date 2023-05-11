package com.pickyberry.rtuitlab_recruit.data.network.details

import com.google.gson.annotations.SerializedName

data class CoinDetailsValuationDto(
    @SerializedName("rub") var rub: Int? = null,
    @SerializedName("usd") var usd: Int? = null
)