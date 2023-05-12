package com.pickyberry.rtuitlab_recruit.domain.model

import com.google.gson.annotations.SerializedName
import com.pickyberry.rtuitlab_recruit.data.network.details.CoinDetailsDescriptionDto
import com.pickyberry.rtuitlab_recruit.data.network.details.CoinDetailsImageDto
import com.pickyberry.rtuitlab_recruit.data.network.details.CoinDetailsLinkDto
import com.pickyberry.rtuitlab_recruit.data.network.details.CoinDetailsMarketDataDto

class CoinDetails(
    val id: String,
    val symbol: String,
    val name: String,
    val hashingAlgorithm: String? = null,
    val description: List<String>? = null,
    val links: List<String>? = null,
    val image: String? = null,
    val marketData: MarketData = MarketData(),
)