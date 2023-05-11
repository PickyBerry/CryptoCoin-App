package com.pickyberry.rtuitlab_recruit.domain.model

import com.google.gson.annotations.SerializedName
import com.pickyberry.rtuitlab_recruit.data.network.details.CoinDetailsDescriptionDto
import com.pickyberry.rtuitlab_recruit.data.network.details.CoinDetailsImageDto
import com.pickyberry.rtuitlab_recruit.data.network.details.CoinDetailsLinkDto
import com.pickyberry.rtuitlab_recruit.data.network.details.CoinDetailsMarketDataDto

class CoinDetails(
    val id: String? = null,
    val symbol: String? = null,
    val name: String? = null,
    val hashingAlgorithm: String? = null,
    val description: List<String>? = null,
    val links: List<String>? = null,
    val image: String? = null,
    val marketData: MarketData = MarketData(),
)