package com.pickyberry.rtuitlab_recruit.domain.model



class CoinDetails(
    val id: String,
    val symbol: String,
    val name: String,
    val hashingAlgorithm: String? = null,
    val description: List<String> = listOf(),
    val links: Map<String,String> = mapOf(),
    val image: String? = null,
    val marketData: MarketData = MarketData(),
)