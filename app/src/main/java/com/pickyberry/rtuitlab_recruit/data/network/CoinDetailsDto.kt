package com.pickyberry.rtuitlab_recruit.data.network

import com.google.gson.annotations.SerializedName
import com.pickyberry.rtuitlab_recruit.data.network.details.CoinDetailsDescriptionDto
import com.pickyberry.rtuitlab_recruit.data.network.details.CoinDetailsImageDto
import com.pickyberry.rtuitlab_recruit.data.network.details.CoinDetailsLinkDto
import com.pickyberry.rtuitlab_recruit.data.network.details.CoinDetailsMarketDataDto

data class CoinDetailsDto(
    @SerializedName("id") val id: String? = null,
    @SerializedName("symbol") val symbol: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("hashing_algorithm") val hashingAlgorithm: String? = null,
    @SerializedName("description") val description: CoinDetailsDescriptionDto? = CoinDetailsDescriptionDto(),
    @SerializedName("links") val links: CoinDetailsLinkDto? = CoinDetailsLinkDto(),
    @SerializedName("image") val image: CoinDetailsImageDto? = CoinDetailsImageDto(),
    @SerializedName("market_data") val marketData: CoinDetailsMarketDataDto? = CoinDetailsMarketDataDto(),
)