package com.pickyberry.rtuitlab_recruit.domain.model


data class MarketData(
    val currentPrice: Currencies? = Currencies(),
    val totalValueLocked: String? = null,
    val marketCap: Currencies? = Currencies(),
    val fullyDilutedValuation: Currencies? = Currencies(),
    val totalVolume: Currencies? = Currencies(),
    val totalSupply: Int? = null,
    val maxSupply: Int? = null,
    val circulatingSupply: Int? = null,
    val lastUpdated: String? = null
)
