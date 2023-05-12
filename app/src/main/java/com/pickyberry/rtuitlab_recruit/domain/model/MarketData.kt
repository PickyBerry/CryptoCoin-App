package com.pickyberry.rtuitlab_recruit.domain.model


data class MarketData(
    val currentPrice: Currencies? = Currencies(),
    val totalValueLocked: String? = null,
    val marketCap: Currencies? = Currencies(),
    val fullyDilutedValuation: Currencies? = Currencies(),
    val totalVolume: Currencies? = Currencies(),
    val totalSupply: Double? = null,
    val maxSupply: Double? = null,
    val circulatingSupply: Double? = null,
)
